package com.whyte.test.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.whyte.test.base.BaseViewModel
import com.whyte.test.base.StatefulResource
import com.whyte.test.data.model.CategoryModel
import com.whyte.test.data.repo.repoImpl.RepoCategoryImpl

class ViewmodelHomeImp(val mRepoCategoryImpl: RepoCategoryImpl) : BaseViewModel() {
    init {
        getCategorys()
    }

    private val mutableApiResponseCategory: MutableLiveData<StatefulResource<CategoryModel?>> =
        MutableLiveData()
    val apiResponseCategory: LiveData<StatefulResource<CategoryModel?>>
        get() = mutableApiResponseCategory

    fun getCategorys() {
        launch {
            val res = mRepoCategoryImpl.getCategory()
            passApiResponseToUiThread(res, mutableApiResponseCategory)
        }
    }

    override fun onRefresh(mRefreshString: String) {

    }

    override fun onNoNetWork(mString: String?) {

    }


}