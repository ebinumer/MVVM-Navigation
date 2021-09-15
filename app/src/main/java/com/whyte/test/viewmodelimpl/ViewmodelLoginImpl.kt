package com.whyte.test.viewmodelimpl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.whyte.test.base.BaseViewModel
import com.whyte.test.base.StatefulResource
import com.whyte.test.data.model.LoginModel
import com.whyte.test.data.repo.repoImpl.RepoLoginImpl

class ViewmodelLoginImpl(val mRepoLoginImpl: RepoLoginImpl) : BaseViewModel() {

    val mutaUserMob: MutableLiveData<String> = MutableLiveData()
    val userMob: LiveData<String>
        get() = mutaUserMob

    val mutaUserPas: MutableLiveData<String> = MutableLiveData()
    val userPas: LiveData<String>
        get() = mutaUserPas


    private val mutableApiResponseLogin: MutableLiveData<StatefulResource<LoginModel?>> =
        MutableLiveData()
    val apiResponseLogin: LiveData<StatefulResource<LoginModel?>>
        get() = mutableApiResponseLogin

    fun login(mobile: String, password: String) {
        launch {
            val res = mRepoLoginImpl.login(mobile, password)
            passApiResponseToUiThread(res, mutableApiResponseLogin)
        }
    }

    fun onLoginClicked() {
        if (userMob.value?.isNotEmpty() == true) {
            if (userPas.value?.isNotEmpty() == true) {
                login(userMob.value.toString(), userPas.value.toString())
            }
        }
    }

    override fun onRefresh(mRefreshString: String) {

    }

    override fun onNoNetWork(mString: String?) {

    }
}