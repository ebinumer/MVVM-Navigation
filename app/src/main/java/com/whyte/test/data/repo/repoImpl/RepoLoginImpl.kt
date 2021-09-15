package com.whyte.test.data.repo.repoImpl

import com.whyte.test.data.api.TestApi
import com.whyte.test.data.model.LoginModel
import com.whyte.test.data.repo.base.BaseRepository
import com.whyte.test.data.repo.base.Resource
import com.whyte.test.data.repo.helpers.DataFetchHelper
import retrofit2.Response

class RepoLoginImpl(
    private val mApi: TestApi
) : BaseRepository() {

    suspend fun login(mobile: String, password: String): Resource<LoginModel?> {
        return object : DataFetchHelper.NetworkOnly<LoginModel?>(
            "login"
        ) {
            override suspend fun getDataFromNetwork() = mApi.login(mobile,password)

            override suspend fun convertApiResponseToData(response: Response<out Any?>) =
                (response.body() as LoginModel)

            override suspend fun storeFreshDataToLocal(data: LoginModel?) = false

        }.fetchDataIOAsync().await()
    }
}