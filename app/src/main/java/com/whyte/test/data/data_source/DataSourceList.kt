package com.whyte.test.data.data_source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.whyte.test.data.model.ListModelData
import com.whyte.test.data.repo.repoImpl.RepoListImpl
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber

class DataSourceList(
    private val mRepo: RepoListImpl,
    private val mScope: CoroutineScope,
    private val mcat:Int,
    private val mSubcat: Int,
    ) : DataSource.Factory<Int, ListModelData>() {

    val mSource: MutableLiveData<DataSourceListCall> = MutableLiveData()
    var mviewType: String = "true"
    override fun create(): DataSource<Int, ListModelData> {
        Timber.e("Reached List DataSourc")
        val data = DataSourceListCall(
            mRepo, mScope, mcat, mSubcat
        )
        this.mSource.postValue(data)
        return data
    }

    fun getSource() = mSource.value

    fun invalidate() = getSource()?.invalidate()


}