package com.whyte.test.utils.extentions

import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String, isLong: Boolean = false) {
    activity?.showToast(message, isLong)
}

fun Fragment.showToast(message: Int, isLong: Boolean = false) {
    activity?.showToast(getString(message), isLong)
}