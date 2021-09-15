package com.whyte.test.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.whyte.test.base.BaseViewModel
import com.whyte.test.data.data_source.DataSourceList
import com.whyte.test.data.model.ListModelData
import com.whyte.test.data.repo.repoImpl.RepoListImpl
import com.whyte.test.utils.SessionManager
import com.whyte.test.utils.pagedListConfig

class ViewmodelItemImpl(
    mSessionManager: SessionManager, mRepo:RepoListImpl):BaseViewModel() {


    val mutableMList: MutableLiveData<String> = MutableLiveData()
     val mList: LiveData<String>
        get() = mutableMList

    private val systemDataSource = DataSourceList(mRepo, ioScope, mSessionManager.Cat!!,0)
     val apiResponseMlistData: LiveData<PagedList<ListModelData>>
        get() = Transformations.switchMap(mList){
            LivePagedListBuilder(
                systemDataSource, pagedListConfig()
            ).build()

        }

    override fun onRefresh(mRefreshString: String) {

    }

    override fun onNoNetWork(mString: String?) {

    }
}