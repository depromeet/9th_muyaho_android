package com.depromeet.muyaho.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.depromeet.muyaho.other.Constants

object PreferenceUtil {

    private const val PREF_NAME = "preference"
    private var preference: SharedPreferences? = null

    private const val KEY_ACCESS_TOKEN = "ACCESS_TOKEN"

    fun init(context: Context) {
        preference = context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE)
    }

    var AccessToken: String
        get() = preference?.getString(KEY_ACCESS_TOKEN, Constants.TEST_SESSION_ID)
            ?: Constants.TEST_SESSION_ID
        set(value) {
            preference?.edit {
                putString(KEY_ACCESS_TOKEN, "Bearer $value")
            }
        }
}