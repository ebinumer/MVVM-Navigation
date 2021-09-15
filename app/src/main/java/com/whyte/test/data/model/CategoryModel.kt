package com.whyte.test.data.model

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryModel(
    @SerializedName("status") var status : Int,
    @SerializedName("message") var message : String,
    @SerializedName("message_ar") var messageAr : String,
    @SerializedName("data") var data : List<CategoryDataModel>
)

@Keep
data class SubCat (

    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("name_ar") var nameAr : String,
    @SerializedName("slug") var slug : String,
    @SerializedName("photo") var photo : String

)

@Keep
data class CategoryDataModel (

    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("name_ar") var nameAr : String,
    @SerializedName("slug") var slug : String,
    @SerializedName("photo") var photo : String,
    @SerializedName("sub_cat_count") var subCatCount : Int,
    @SerializedName("sub_cat") var subCat : List<SubCat>

)

class DiffUtilSubCatData(
    private val oldList: List<SubCat>,
    private val newList: List<SubCat>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}

class DiffUtilCategorData(
    private val oldList: List<CategoryDataModel>,
    private val newList: List<CategoryDataModel>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
