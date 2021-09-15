package com.whyte.test.data.repo.helpers

import android.os.Looper
import androidx.annotation.WorkerThread
import com.google.gson.Gson
import com.whyte.test.data.repo.base.Resource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import com.whyte.test.data.repo.helpers.DataFetchHelper.DataFetchStyle.*

abstract class DataFetchHelper<T>(
    val tag: String,
    private val dataFetchStyle: DataFetchStyle? = NETWORK_FIRST_LOCAL_FAILOVER
) {


    enum class DataFetchStyle {
        NETWORK_ONLY,
        NETWORK_FIRST_LOCAL_FAILOVER,
        LOCAL_ONLY;

        enum class Result {
            NO_FETCH,
            LOCAL_DATA_NETWORK_FAIL,
            LOCAL_DATA_ONLY,
            NETWORK_DATA_FIRST,
            NETWORK_DATA_ONLY
        }
    }

    /**
     * Inner classes to compliment specific styles
     */
    abstract class LocalOnly<S>(
        tag: String
    ) : DataFetchHelper<S>(
        tag,
        LOCAL_ONLY
    ) {
        abstract override suspend fun getDataFromLocal(): S
    }


    abstract class NetworkOnly<S>(
        tag: String
    ) : DataFetchHelper<S>(tag, NETWORK_ONLY) {
        abstract override suspend fun getDataFromNetwork(): Response<out Any?>
        abstract override suspend fun convertApiResponseToData(response: Response<out Any?>): S
        abstract override suspend fun storeFreshDataToLocal(data: S): Boolean
    }


    abstract class NetworkFirstLocalFailover<S>(
        tag: String
    ) : DataFetchHelper<S>(
        tag,
        NETWORK_FIRST_LOCAL_FAILOVER
    ) {
        abstract override suspend fun getDataFromLocal(): S
        abstract override suspend fun getDataFromNetwork(): Response<out Any?>
        abstract override suspend fun convertApiResponseToData(response: Response<out Any?>): S
        abstract override suspend fun storeFreshDataToLocal(data: S): Boolean
    }

    @WorkerThread
    open suspend fun getDataFromLocal(): T? {
        throw NotImplementedError("getDataFromLocal should be implemented to support $dataFetchStyle")
    }

    @WorkerThread
    open suspend fun getDataFromNetwork(): Response<out Any?> {
        throw NotImplementedError("getDataFromNetwork should be implemented to support $dataFetchStyle")
    }

    open suspend fun convertApiResponseToData(response: Response<out Any?>): T {
        try {
            return response.body() as T
        } catch (e: Exception) {
            throw ClassCastException(
                "$e - Cannot convert ${response.body()!!::class.java.simpleName} to Data Fetch type, " +
                        "override this method to provide conversion."
            )
        }
    }

    open suspend fun storeFreshDataToLocal(data: T): Boolean {
        throw NotImplementedError("storeFreshDataToLocal should be implemented to support $dataFetchStyle")
    }

    open suspend fun operateOnDataPostFetch(data: T) {

    }


    suspend fun fetchDataIOAsync(): Deferred<Resource<T>> = withContext(Dispatchers.IO) {
        async {
            fetchDataByStyle()
        }
    }

    private suspend fun fetchDataByStyle(
    ): Resource<T> {
        val resource = Resource<T>()
        resource.dataFetchStyleResult = Result.NO_FETCH
        resource.dataFetchStyle = dataFetchStyle ?: NETWORK_FIRST_LOCAL_FAILOVER

        if (onMainThread()) {
            throw IllegalThreadStateException("Cannot perform Network nor Local storage transactions on main thread!")
        }

        when (dataFetchStyle) {
            NETWORK_FIRST_LOCAL_FAILOVER -> {
                resource.data = refreshDataFromNetwork(resource, NETWORK_FIRST_LOCAL_FAILOVER)
                if (resource.data == null) {
                    log("Unable to get data from network, failing over to local")
                    resource.data = getDataFromLocal()
                    resource.fresh = false
                    resource.dataFetchStyleResult = Result.LOCAL_DATA_NETWORK_FAIL
                } else {
                    resource.fresh = true
                    resource.dataFetchStyleResult = Result.NETWORK_DATA_FIRST
                }
            }
            LOCAL_ONLY -> {
                resource.data = getDataFromLocal()
                resource.fresh = true
                resource.dataFetchStyleResult = Result.LOCAL_DATA_ONLY
            }
            NETWORK_ONLY -> {
                resource.data = refreshDataFromNetwork(resource, NETWORK_ONLY)
                resource.fresh = true
                resource.dataFetchStyleResult = Result.NETWORK_DATA_ONLY
            }
        }
        resource.data?.let {
            operateOnDataPostFetch(it)
        }
        return resource
    }

    private suspend fun refreshDataFromNetwork(
        resource: Resource<T>,
        dataFetchStyle: DataFetchStyle
    ): T? {
        val forceStoreLocally = arrayListOf(
            NETWORK_FIRST_LOCAL_FAILOVER
        ).contains(dataFetchStyle)

        val response: Response<out Any?>
        var convertedToData: T? = null
        var storedFreshData = false
        try {
            response =
                getDataFromNetwork()
            Timber.e("Called url=${response.raw().request.url}")
            Timber.e("Data from network=${Gson().toJson(response.body())}")
            resource.response = response
            if (response.code() == 422 || response.code()==404) {
                val errorBody=response.errorBody()?.string()
                Timber.e("errorBody Get=$errorBody")
                resource.errorData = errorBody
                resource.isUnResponsibleEntity=true
            }
            if (resource.response?.body() == null) {
                resource.errorMessage =
                    "Response body was null! Verify correct response object was used for service call"
                Timber.e(resource.errorMessage)
                return null
            }

            convertedToData = convertApiResponseToData(response)
            log("Converted data for storage")
            storedFreshData = storeFreshDataToLocal(convertedToData)
        } catch (e: Exception) {
            resource.throwable = e
            resource.errorMessage =
                "Unable to refresh data for request type $tag, due to exception $e"
            Timber.e(resource.errorMessage)
        } finally {
            if (forceStoreLocally && !storedFreshData) {
                Timber.e("$dataFetchStyle requires data to be stored locally for the style to work correctly!")
            }
        }
        return convertedToData
    }

    private fun log(message: String) {
        Timber.i("$tag - $message")
    }
}

fun onMainThread() = Looper.myLooper() == Looper.getMainLooper()