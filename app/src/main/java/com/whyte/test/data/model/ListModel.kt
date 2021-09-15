package com.whyte.test.data.model

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
@Keep
data class ListModel(
    @SerializedName("status") var status : Int,
    @SerializedName("total") var total : Int,
    @SerializedName("message") var message : String,
    @SerializedName("message_ar") var messageAr : String,
    @SerializedName("data") var data : List<ListModelData>

)
@Keep
data class ListModelData (

    @SerializedName("products_id") var productsId : Int,
    @SerializedName("brand_name") var brandName : String,
    @SerializedName("brand_name_ar") var brandNameAr : String,
    @SerializedName("name") var name : String,
    @SerializedName("name_ar") var nameAr : String,
    @SerializedName("photo") var photo : String,
    @SerializedName("price") var price : Int,
    @SerializedName("previous_price") var previousPrice : Int,
    @SerializedName("wish_list") var wishList : Int

)


data class ListReqModel(
    val main_category:String,
    val sub_category:String,
    val child_category:String,
    val keywords:String,
    val price_filter:String,
    val shop_id:String,
    val page:String,
    val new_collection:String,
    val best_sellers:String,
    val brands_id:String
)

val ListModelDataDiffUtil = object : DiffUtil.ItemCallback<ListModelData>() {
    override fun areItemsTheSame(oldItem: ListModelData, newItem: ListModelData): Boolean {
        return oldItem.productsId == newItem.productsId
    }

    override fun areContentsTheSame(oldItem: ListModelData, newItem: ListModelData): Boolean {
        return oldItem == newItem
    }

}