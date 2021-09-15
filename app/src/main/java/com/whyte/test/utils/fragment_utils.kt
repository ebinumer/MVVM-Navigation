package com.whyte.test.utils

import android.app.Activity
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.whyte.test.R
import com.whyte.test.utils.extentions.showToast
import timber.log.Timber

fun Fragment.naviGateTo(mAction: NavDirections) {
    try {
        findNavController().navigate(mAction)
    } catch (e: IllegalArgumentException) {
        //Calling base Fragment
        Timber.e("Data get=${e.toString()}")
        activity?.let {
            Navigation.findNavController(it, R.id.mainNavHost).navigate(
                mAction
            )
        }
    }
}

fun Fragment.naviGateTo(@NonNull directions: NavDirections, @Nullable navOptions: NavOptions) {
    findNavController().navigate(directions, navOptions)
}

fun Fragment.naviGateTo(@NonNull directions: NavDirections, @Nullable navigatorExtras: Navigator.Extras) {
    findNavController().navigate(directions, navigatorExtras)
}


fun Fragment.showMessage(mString: String) {
    activity?.showToast(mString, false)
}


fun Fragment.moveToBackStack() {
    try {
        findNavController().popBackStack()
    } catch (e: Exception) {
        Timber.e("E =${e.toString()}")
        Timber.e("E =${e.message}")
    }
}