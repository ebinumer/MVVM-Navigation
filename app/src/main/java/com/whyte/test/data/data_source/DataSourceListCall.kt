package com.whyte.test.data.data_source

import com.google.gson.Gson
import com.whyte.test.R
import com.whyte.test.base.BaseDataSource
import com.whyte.test.base.StatefulResource
import com.whyte.test.data.model.ListModelData
import com.whyte.test.data.repo.repoImpl.RepoListImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class DataSourceListCall (private val mRepo: RepoListImpl,
                          private val mScope: CoroutineScope,
                          private val mcat:Int,
                          private val mSubcat: Int,
) : BaseDataSource<ListModelData>() {


    override fun getScope() = mScope


    override fun executeQuery(
        mPage: Int,
        mCallback: ((List<ListModelData>) -> Any)
    ) {
        mScope.launch {
            Timber.e("Reached in List  fetching")
            if (mPage == 1) {
                mNetworkState.postValue(StatefulResource.loading())
            }
            val res = mRepo.getList(mPage,mcat)
            when {
                res.hasData() -> {
                    res.data?.let {
                        Timber.e("Reached in List fetching data=${Gson().toJson(it)}")
                        mNetworkState.postValue(StatefulResource.success())
                        it.data.let { it1 -> mCallback(it1) }
                    }
                }
                res.isNetworkIssue() -> {
                    Timber.e("Network Error In paging")
                    mNetworkState.postValue(
                        StatefulResource<List<ListModelData>?>().apply {
                            state = StatefulResource.State.ERROR_NETWORK
                            message = R.string.someErrorInApi
                        }
                    )
                }
                res.isApiIssue() -> {
                    mNetworkState.postValue(
                        StatefulResource<List<ListModelData>?>().apply {
                            state = StatefulResource.State.ERROR_API
                            message = R.string.service_error
                        }
                    )
                }
                else -> {
                    Timber.e("Data here 5")
                    mNetworkState.postValue(
                        StatefulResource<List<ListModelData>?>()
                            .apply {
                                state = StatefulResource.State.SUCCESS
                                message = R.string.someErrorInApi
                            })
                }
            }
        }
    }

}