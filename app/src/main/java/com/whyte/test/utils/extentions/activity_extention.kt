package com.whyte.test.utils.extentions

import android.app.Activity
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.whyte.test.R

fun Activity.showToast(message: String, isLong: Boolean = false) {
    val inflater = this.layoutInflater
    val layouttoast = inflater.inflate(R.layout.toastcustom, null) as ViewGroup
    val textView = layouttoast.findViewById(R.id.texttoast) as TextView
    textView.text = message + ""
    val scale = resources.displayMetrics.density
    val mytoast = Toast(this)
    mytoast.view = layouttoast
    val offsetY = (-100 * scale).toInt()
    mytoast.setGravity(Gravity.CENTER_VERTICAL,0, offsetY)
    mytoast.duration = if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    mytoast.show()
}

fun Activity.showToast(message: Int, isLong: Boolean = false) {
    val inflater = this.layoutInflater
    val layouttoast = inflater.inflate(R.layout.toastcustom, null) as ViewGroup
    val textView = layouttoast.findViewById(R.id.texttoast) as TextView
    textView.text = getString(message) + ""
    val scale = resources.displayMetrics.density
    val mytoast = Toast(this)
    mytoast.view = layouttoast
    val offsetY = (-100 * scale).toInt()
    mytoast.setGravity(Gravity.CENTER_VERTICAL,0, offsetY)
    mytoast.duration = if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    mytoast.show()
}