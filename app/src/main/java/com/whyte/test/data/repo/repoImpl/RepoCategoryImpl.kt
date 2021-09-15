package com.whyte.test.data.repo.repoImpl

import com.whyte.test.data.api.TestApi
import com.whyte.test.data.model.CategoryModel
import com.whyte.test.data.repo.base.BaseRepository
import com.whyte.test.data.repo.base.Resource
import com.whyte.test.data.repo.helpers.DataFetchHelper
import retrofit2.Response

class RepoCategoryImpl(
    private val mApi: TestApi
) : BaseRepository() {

    suspend fun getCategory(): Resource<CategoryModel?> {
        return object : DataFetchHelper.NetworkOnly<CategoryModel?>(
            "get Category"
        ) {
            override suspend fun getDataFromNetwork() = mApi.getCategory()

            override suspend fun convertApiResponseToData(response: Response<out Any?>) =
                (response.body() as CategoryModel)

            override suspend fun storeFreshDataToLocal(data: CategoryModel?) = false

        }.fetchDataIOAsync().await()
    }
}