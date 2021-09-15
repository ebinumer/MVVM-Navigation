package com.whyte.test.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class LoginModel(
    @SerializedName("status") var status : Int,
    @SerializedName("message") var message : String,
    @SerializedName("message_ar") var messageAr : String,
    @SerializedName("data") var data : LoginData
)
@Keep
data class LoginData(

    @SerializedName("token") var token : String,
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("email") var email : String,
    @SerializedName("phone") var phone : String,
    @SerializedName("dob") var dob : String,
    @SerializedName("gender") var gender : String,
    @SerializedName("notification") var notification : String,
    @SerializedName("photo") var photo : String
)
