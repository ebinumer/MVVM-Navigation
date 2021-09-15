package com.whyte.test.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SessionManager@SuppressLint("CommitPrefEdits") constructor(private val context: Context)  {

    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val TOKEN = "api_token"
    private val USERNAME="username"
    private val APP_OPEN_STATUS = "appOpenStatus"
    private val APP_LANGUAGE="applanguage"

    /*App open status*/
    var appOpenStatus: Boolean
        get() = pref.getBoolean(APP_OPEN_STATUS, false)
        set(value) {
            editor.putBoolean(APP_OPEN_STATUS, value).apply()
        }

    /*set token*/
    var token: String?
        get() = pref.getString(TOKEN, "")
        set(token) {
            editor.putString(TOKEN, token)
            editor.apply()
        }
    /*set username*/
    var Cat: Int?
        get() = pref.getInt(USERNAME, 0)
        set(cat) {
            if (cat != null) {
                editor.putInt(USERNAME, cat)
            }
            editor.apply()
        }
    /*set session*/
    var appLanguage: String?
        get() = pref.getString(APP_LANGUAGE, "en")
        set(appLanguage) {
            editor.putString(APP_LANGUAGE, appLanguage)
            editor.apply()
        }

    companion object {
        private const val PREF_NAME = "Ecommerce_Session"



    }
    init {
        // Shared pref mode
        val PRIVATE_MODE = 0
        pref = context.getSharedPreferences(
            PREF_NAME,
            PRIVATE_MODE
        )
        editor = pref.edit()
    }
}