package com.whyte.test.data.repo.base

import com.whyte.test.data.repo.helpers.DataFetchHelper
import retrofit2.Response
import java.io.IOException

class Resource<T> {

    var errorData: String? = null

    var data: T? = null
    var response: Response<out Any?>? = null

    var throwable: Throwable? = null

    var dataFetchStyle =
        DataFetchHelper.DataFetchStyle.NETWORK_FIRST_LOCAL_FAILOVER

    var dataFetchStyleResult = DataFetchHelper.DataFetchStyle.Result.NO_FETCH

    fun hasData() = data != null

    var fresh: Boolean = false

    var errorMessage: String? = null

    fun isNetworkIssue(): Boolean = throwable is IOException

    fun isApiIssue(): Boolean = !(response?.isSuccessful ?: true)

    fun isUnResponsibleEntityFun(): Boolean = errorData?.let {
        true
    } ?: false

    var isUnResponsibleEntity: Boolean = false

    fun <S : Any?> copy(newData: S): Resource<S> {
        return Resource<S>().apply {
            data = newData
            response = this@Resource.response
            throwable = this@Resource.throwable
            dataFetchStyle = this@Resource.dataFetchStyle
            dataFetchStyleResult = this@Resource.dataFetchStyleResult
            fresh = this@Resource.fresh
            errorMessage = this@Resource.errorMessage
        }
    }
}