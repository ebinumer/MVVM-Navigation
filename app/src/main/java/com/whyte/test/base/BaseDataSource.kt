package com.whyte.test.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

abstract class BaseDataSource<T>() : PageKeyedDataSource<Int, T>() {

    private var mSuperVisorJob = SupervisorJob()
    private var retryQuery: (() -> Any)? = null



    abstract fun getScope(): CoroutineScope

    abstract fun executeQuery(mPage: Int, mCallback: ((List<T>) -> Any))

    fun getNetworkState() = mNetworkState

    fun refreshData() = this.invalidate()

    val isRefreshing: MutableLiveData<ObservableBoolean> = MutableLiveData()

    val mNetworkState: MutableLiveData<StatefulResource<List<T>?>> = MutableLiveData()

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        retryQuery = { loadAfter(params, callback) }
        executeQuery(params.key) {
            callback.onResult(it, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
//       Not implemeted
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(1) {
            callback.onResult(it, null, 2)
        }
    }

}