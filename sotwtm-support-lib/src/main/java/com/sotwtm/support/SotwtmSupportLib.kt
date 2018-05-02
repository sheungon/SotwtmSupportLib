package com.sotwtm.support

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.databinding.ObservableField
import android.support.v4.os.LocaleListCompat
import com.sotwtm.support.SotwtmSupportLib.Companion.getInstance
import com.sotwtm.support.SotwtmSupportLib.Companion.init
import com.sotwtm.support.util.locale.AppHelpfulLanguageUtil
import com.sotwtm.util.Log
import java.lang.ref.WeakReference
import java.util.*

/**
 * Call [init] before [getInstance] to use this class.
 * */
@SuppressLint("CommitPrefEdits")
class SotwtmSupportLib
private constructor(_application: Application, _supportedLocales: List<Locale>?) {

    private val application: Application = _application
    private val sharedPreferences: SharedPreferences by lazy { application.getSharedPreferences(DEFAULT_SHARED_PREF_FILE, Context.MODE_PRIVATE) }
    private val editor: SharedPreferences.Editor by lazy { sharedPreferences.edit() }
    private var supportedLocales: List<Locale>? = _supportedLocales

    /**
     * The app locale currently using.
     * */
    val appLocale: ObservableField<Locale> = object : ObservableField<Locale>() {
        @Synchronized
        override fun get(): Locale =
                sharedPreferences.getString(PREF_KEY_APP_LOCALE, null)?.let {
                    val languageTags = LocaleListCompat.forLanguageTags(it)
                    if (languageTags.isEmpty) AppHelpfulLanguageUtil.getDefaultLangFromSystemSetting(application, supportedLocales)
                    else languageTags[0]
                }
                        ?: AppHelpfulLanguageUtil.getDefaultLangFromSystemSetting(application, supportedLocales)

        @Synchronized
        override fun set(value: Locale) {
            if (get() == value) {
                return
            }

            editor.putString(PREF_KEY_APP_LOCALE, LocaleListCompat.create(value).toLanguageTags())
            editor.apply()
            notifyChange()
        }
    }

    init {
        AppHelpfulLanguageUtil.setAppLocale(application, appLocale.get()!!)
    }


    /**
     * Set the supported locales for this [Application].
     * It current app locale is not in the the newly given supported locale list,
     * [appLocale] will be updated by [AppHelpfulLanguageUtil.getDefaultLangFromSystemSetting]
     *
     * @param newLocales Giving this null means all locales will be supported.
     * @see appLocale
     * */
    @Synchronized
    fun updateSupportedLocales(newLocales: List<Locale>?) {
        val currentAppLocale = appLocale.get()!!
        supportedLocales = newLocales

        if (newLocales != null) {
            // Check if the current locale is in the new supported locale list
            if (!newLocales.any { AppHelpfulLanguageUtil.equals(currentAppLocale, it) }) {
                // If the current locale is not supported anymore, update current locale to system best matched
                appLocale.set(AppHelpfulLanguageUtil.getDefaultLangFromSystemSetting(application, newLocales))
            }
        }

    }

    fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    companion object {
        @JvmStatic
        private val PREF_KEY_APP_LOCALE = "AppLocale"
        @JvmStatic
        private val DEFAULT_SHARED_PREF_FILE = "sotwtm-support-lib"
        @JvmStatic
        private var INSTANCE: SotwtmSupportLib? = null

        @JvmStatic
        fun init(application: Application, supportedLocales: List<Locale>?) {
            INSTANCE = SotwtmSupportLib(application, supportedLocales)
        }

        @JvmStatic
        fun getInstance(): SotwtmSupportLib = INSTANCE
                ?: throw Exception("Please init SotwtmSupportLib first.")
    }

    class OnAppLocaleChangedListener(activity: Activity) : SharedPreferences.OnSharedPreferenceChangeListener {

        private val activityRef = WeakReference(activity)

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            if (key == PREF_KEY_APP_LOCALE) {
                Log.d("Lang changed")
                activityRef.get()?.recreate()
            }
        }
    }
}