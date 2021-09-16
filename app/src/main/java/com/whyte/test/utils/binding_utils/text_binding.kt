package com.whyte.test.utils.binding_utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.whyte.test.R
import com.whyte.test.data.model.CategoryDataModel
import com.whyte.test.data.model.ListModelData
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

@BindingAdapter("brandName")
fun setBrandName(mView: TextView, mName: ListModelData?) {
    mName?.let {
        val mSessionManager = object : KoinComponent {
            val ms by inject<SessionManager>()
        }.ms
        mView.text = when (mSessionManager.appLanguage) {
            "en" -> mName.brandName
            "ar" -> mName.brandNameAr
            else -> mName.brandName
        }
    }
}
@BindingAdapter("prodectName")
fun setProductName(mView: TextView, mName: ListModelData?) {
    mName?.let {
        val mSessionManager = object : KoinComponent {
            val ms by inject<SessionManager>()
        }.ms
        mView.text = when (mSessionManager.appLanguage) {
            "en" -> mName.name
            "ar" -> mName.nameAr
            else -> mName.name
        }
    }
}

@BindingAdapter("productPrice")
fun setProductPrice(mView: TextView, nValues: Int?) {
    nValues?.let {
        val mSessionManager = object : KoinComponent {
            val ms by inject<SessionManager>()
        }.ms
        mView.text = when (mSessionManager.appLanguage) {
            "en" -> "${mView.context.getString(R.string.product_price)}$nValues"
            "ar" -> "${mView.context.getString(R.string.product_price)}$nValues"
            else -> "${mView.context.getString(R.string.product_price)}$nValues"
        }
    }
}
