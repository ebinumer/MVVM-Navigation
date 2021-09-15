package com.whyte.test.base

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.whyte.test.R
import com.whyte.test.data.repo.base.Resource
import com.whyte.test.utils.EventUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {
    /**
     * Coroutines in a Pool of thread
     */
    protected val ioScope = CoroutineScope(Dispatchers.Default)
    //    abstract val mInterface: InterfaceEmpty
    fun launch(blockScope: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(block = blockScope)

    fun async(blockScope: suspend CoroutineScope.() -> Unit) =
        viewModelScope.async(block = blockScope)

    /**
     * Observable boolean that observe the chnages and
     * populate the visubilty according to boolean values
     */
    open val isProgressBarVisible: ObservableBoolean = ObservableBoolean()
    open fun setIsProgressBarVisible(boolean: Boolean) {
        isProgressBarVisible.set(boolean)
    }


    open val showNoNetworkLayout: ObservableBoolean = ObservableBoolean()


    open val showToastMessage: MutableLiveData<String> = MutableLiveData()

    open fun setToastMessage(mData: String) {
        showToastMessage.value = mData
    }


    open val showToastMessageFromResources: MutableLiveData<Int> = MutableLiveData()

    open val setScreenNotTouchable: MutableLiveData<Boolean> = MutableLiveData()

    open fun setScreenNotTouchable(boolean: Boolean) {
        setScreenNotTouchable.value = boolean
    }

    fun setToastMessageFromResources(mData: Int) {
        showToastMessageFromResources.value = mData
    }

    abstract fun onRefresh(mRefreshString: String)

    abstract fun onNoNetWork(mString: String?)

    private fun setShowNoNetworkLayout(visibility: Boolean) {
        showNoNetworkLayout.set(visibility)
    }


    fun <T> passApiResponseToUiThread(
        res: Resource<T>,
        mutableData: MutableLiveData<StatefulResource<T>>,
        observableBoolean: ObservableBoolean = isProgressBarVisible,
        showNonNetworkLayoutNeeded: Boolean = false,
        setScreenNotTouchableGet: Boolean = false
    ) {
        observableBoolean.set(false)
        setScreenNotTouchable.value = false
        when {
            res.hasData() -> {
                setShowNoNetworkLayout(false)
                Timber.e("has data res=${Gson().toJson(res.fresh)}")
                Timber.e("has data res=${Gson().toJson(res.data)}")
                mutableData.value = StatefulResource.success(res)
            }
            res.isNetworkIssue() -> {
                if (showNonNetworkLayoutNeeded) {
                    Timber.e("Show Network Layout")
                    showNoNetworkLayout.set(true)
                    Timber.e("Visibility=${showNoNetworkLayout.get()}")
                }
                Timber.e("isNetworkIssue1=${Gson().toJson(res.errorMessage)}")
                Timber.e("isNetworkIssue1=${Gson().toJson(res.fresh)}")
                setToastMessageFromResources(R.string.no_network_connection)
            }
            res.isUnResponsibleEntityFun() -> {
                Timber.e("isUnResponsibleEntity=${Gson().toJson(res.errorMessage)}")
                Timber.e("isUnResponsibleEntity=${Gson().toJson(res.fresh)}")
                if (showNonNetworkLayoutNeeded) {
                    showNoNetworkLayout.set(false)
                }
                res.errorData?.let { errorData ->
                    Timber.e("Error data2=$errorData")
                    val gson = Gson().fromJson(errorData, JsonObject::class.java)
                    Timber.e("msg=${gson.get("message").asString}")
//                    val currentLang: String = Locale.getDefault().getLanguage()
//                    Timber.e("currentLang=${currentLang}")
                    mutableData.value = StatefulResource.empty(gson.get("message").asString)
//                    if(gson.get("message").asString =="Wishlist Empty"){
//
//                        setToastMessageFromResources(R.string.wishlist_empty)
//
//                    }
//                    else if(gson.get("message").asString =="Cart Empty"){
//
//                        setToastMessageFromResources(R.string.cart_empty)
//
//                    }
//                    else {
//                        setToastMessage(gson.get("message").asString)
//                    }
                }
            }
            res.isApiIssue() -> {
                Timber.e("isApiIssue=${Gson().toJson(res.errorMessage)}")
                Timber.e("isApiIssue=${Gson().toJson(res.fresh)}")
                if (showNonNetworkLayoutNeeded) {
                    showNoNetworkLayout.set(false)
                }
                setToastMessageFromResources(R.string.service_error)
            }
            else -> {
                Timber.e("other error=${Gson().toJson(res.errorMessage)}")
                Timber.e("other error=${Gson().toJson(res.fresh)}")
                setToastMessageFromResources(R.string.someErrorInApi)
            }
        }
    }


    fun <T> passApiResponseToUiThreadOnce(
        res: Resource<T>,
        mutableData: MutableLiveData<EventUtil<StatefulResource<T>>>,
        observableBoolean: ObservableBoolean = isProgressBarVisible,
        showNonNetworkLayoutNeeded: Boolean = false,
    ) {
        observableBoolean.set(false)
        setScreenNotTouchable.value = false
        when {
            res.hasData() -> {
                Timber.e("res1=${Gson().toJson(res.fresh)}")
                Timber.e("res2=${Gson().toJson(res.data)}")
                mutableData.value = EventUtil(StatefulResource.success(res))
            }
            res.isNetworkIssue() -> {
                Timber.e("isNetworkIssue2=${Gson().toJson(res.errorMessage)}")
                Timber.e("isNetworkIssue2=${Gson().toJson(res.fresh)}")
                setToastMessageFromResources(R.string.no_network_connection)

            }
            res.isUnResponsibleEntityFun() -> {
                Timber.e("isUnResponsibleEntity=${Gson().toJson(res.errorMessage)}")
                Timber.e("isUnResponsibleEntity=${Gson().toJson(res.fresh)}")
                res.errorData?.let { errorData ->
                    Timber.e("Error data1=$errorData")
                    val gson = Gson().fromJson(errorData, JsonObject::class.java)
//                    setToastMessage(gson.get("message").asString)
                    mutableData.value = EventUtil(StatefulResource.empty(gson.get("message").asString))
                }
            }
            res.isApiIssue() -> {
                Timber.e("isApiIssue=${Gson().toJson(res.errorMessage)}")
                Timber.e("isApiIssue=${Gson().toJson(res.fresh)}")
                setToastMessageFromResources(R.string.service_error)
            }
            else -> {
                Timber.e("other error=${Gson().toJson(res.errorMessage)}")
                Timber.e("other error=${Gson().toJson(res.fresh)}")
                setToastMessageFromResources(R.string.someErrorInApi)
            }
        }
    }


    fun <T> handleNetworkResponses(
        data: MutableLiveData<StatefulResource<List<T>?>>,
        showNonNetworkLayoutNeeded: Boolean = false
    ) {
        val networkState = data.value
        networkState?.let {
            Timber.e("Network state value=${it.state.name}")
            if (showNonNetworkLayoutNeeded)
                setShowNoNetworkLayout(true)
            else setShowNoNetworkLayout(false)
            when (it.state) {
                StatefulResource.State.LOADING ->
                    setIsProgressBarVisible(true)
                else -> setIsProgressBarVisible(false)
            }
        }
    }


    fun <T> handleNetworkResponses(
        data: StatefulResource<List<T>?>,
        showNonNetworkLayoutNeeded: Boolean = false
    ) {
        val networkState = data
        networkState.let {
            Timber.e("Network state value=${it.state.name}")
            when (it.state) {
                StatefulResource.State.ERROR_NETWORK -> {
                    if (showNonNetworkLayoutNeeded) {
                        setIsProgressBarVisible(false)
                        setShowNoNetworkLayout(true)
                    } else {
                        setIsProgressBarVisible(true)
                        setShowNoNetworkLayout(false)
                    }
                }
                StatefulResource.State.LOADING ->
                    setIsProgressBarVisible(true)
                else -> {
                    setShowNoNetworkLayout(false)
                    setIsProgressBarVisible(false)
                }
            }
        }
    }

}