package com.whyte.test.utils.binding_utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.whyte.test.data.model.CategoryDataModel
import com.whyte.test.data.model.SubCat
import com.whyte.test.utils.SessionManager
import org.koin.core.KoinComponent
import org.koin.core.inject

@BindingAdapter("setCategoryName")
fun setProductName(mView: TextView, mName: CategoryDataModel?) {
    mName?.let {
        val mSessionManager = object : KoinComponent {
            val ms by inject<SessionManager>()
        }.ms
        mView.text = when (mSessionManager.appLanguage) {
            "en" -> {
                mName.name
            }
            "ar" -> {
                mName.nameAr
            }
            else -> {
                mName.name
            }
        }
    }
}

@BindingAdapter("setSubCategoryName")
fun setSubProductName(mView: TextView, mName: SubCat?) {
    mName?.let {
        val mSessionManager = object : KoinComponent {
            val ms by inject<SessionManager>()
        }.ms
        mView.text = when (mSessionManager.appLanguage) {
            "en" -> {
                mName.name
            }
            "ar" -> {
                mName.nameAr
            }
            else -> {
                mName.name
            }
        }
    }
}