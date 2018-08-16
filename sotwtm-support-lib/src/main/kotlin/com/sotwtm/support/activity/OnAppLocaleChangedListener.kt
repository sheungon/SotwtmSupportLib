package com.sotwtm.support.activity

import android.app.Activity
import android.content.SharedPreferences
import com.sotwtm.support.SotwtmSupportLib
import com.sotwtm.util.Log
import java.lang.ref.WeakReference

class OnAppLocaleChangedListener(activity: Activity) : SharedPreferences.OnSharedPreferenceChangeListener {

    private val activityRef = WeakReference(activity)

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == SotwtmSupportLib.PREF_KEY_APP_LOCALE) {
            Log.d("Lang changed")
            activityRef.get()?.recreate()
        }
    }
}