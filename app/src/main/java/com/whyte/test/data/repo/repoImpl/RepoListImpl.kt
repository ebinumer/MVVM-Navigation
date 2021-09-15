package com.whyte.test.data.repo.repoImpl

import com.whyte.test.data.api.TestApi
import com.whyte.test.data.model.ListModel
import com.whyte.test.data.repo.base.BaseRepository
import com.whyte.test.data.repo.base.Resource
import com.whyte.test.data.repo.helpers.DataFetchHelper
import retrofit2.Response

class RepoListImpl(
    private val mApi: TestApi
) : BaseRepository() {

    suspend fun getList(page: Int, main_category: Int,sub_category: Int): Resource<ListModel?> {
        return object : DataFetchHelper.NetworkOnly<ListModel?>(
            "get List"
        ) {
            override suspend fun getDataFromNetwork() = mApi.getList(page, main_category, sub_category)

            override suspend fun convertApiResponseToData(response: Response<out Any?>) =
                (response.body() as ListModel)

            override suspend fun storeFreshDataToLocal(data: ListModel?) = false

        }.fetchDataIOAsync().await()
    }
}