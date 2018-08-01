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
import com.sotwtm.support.util.locale.AppHelpfulLocaleUtil
import com.sotwtm.support.util.singleton.SingletonHolder1
import com.sotwtm.util.Log
import java.lang.ref.WeakReference
import java.util.*

/**
 * Call [init] before [getInstance] to use this class.
 * */
@SuppressLint("CommitPrefEdits")
class SotwtmSupportLib
private constructor(_application: Application) {

    private val application: Application = _application
    private val sharedPreferences: SharedPreferences by lazy { application.getSharedPreferences(DEFAULT_SHARED_PREF_FILE, Context.MODE_PRIVATE) }
    private val editor: SharedPreferences.Editor by lazy { sharedPreferences.edit() }

    /**
     * The app locale currently using.
     * */
    val appLocale: ObservableField<Locale> = object : ObservableField<Locale>() {
        @Synchronized
        override fun get(): Locale =
                sharedPreferences.getString(PREF_KEY_APP_LOCALE, null)?.let {
                    val languageTags = LocaleListCompat.forLanguageTags(it)
                    if (languageTags.isEmpty) AppHelpfulLocaleUtil.getDefaultLangFromSystemSetting(supportedLocales.get())
                    else languageTags[0]
                }
                        ?: AppHelpfulLocaleUtil.getDefaultLangFromSystemSetting(supportedLocales.get())

        @Synchronized
        override fun set(value: Locale) {
            if (get() == value) {
                return
            }

            editor.putString(PREF_KEY_APP_LOCALE, LocaleListCompat.create(AppHelpfulLocaleUtil.unify(value)).toLanguageTags())
            editor.apply()
            notifyChange()
        }
    }

    var supportedLocales: ObservableField<List<Locale>> = object : ObservableField<List<Locale>>(emptyList()) {

        @Synchronized
        override fun get(): List<Locale> {
            val supportedLocalesString = sharedPreferences.getString(PREF_KEY_SUPPORTED_LOCALES, null)
                    ?: return defaultSupportedLocales
            return supportedLocalesString.split(SEPARATOR_LOCALE).mapNotNull {
                LocaleListCompat.forLanguageTags(it).get(0)
            }
        }

        @Synchronized
        override fun set(newLocales: List<Locale>?) {

            if (newLocales == null || newLocales.isEmpty()) {
                // Remove the list means supported all locale
                editor.remove(PREF_KEY_SUPPORTED_LOCALES)
                editor.apply()
                notifyChange()
            } else {
                val currentAppLocale = appLocale.get()!!
                // Check if the current locale is in the new supported locale list
                if (!newLocales.any { AppHelpfulLocaleUtil.equals(currentAppLocale, it) }) {
                    // If the current locale is not supported anymore, update current locale to system best matched
                    resetLocaleToSystemBestMatched(newLocales)
                }
                val localeListString = newLocales.joinToString(separator = SEPARATOR_LOCALE,
                        transform = { LocaleListCompat.create(AppHelpfulLocaleUtil.unify(it)).toLanguageTags() })
                if (localeListString != sharedPreferences.getString(PREF_KEY_SUPPORTED_LOCALES, null)) {
                    editor.putString(PREF_KEY_SUPPORTED_LOCALES, localeListString)
                    editor.apply()
                    notifyChange()
                }
            }
        }
    }

    init {
        AppHelpfulLocaleUtil.setAppLocale(application, appLocale.get()!!)
    }


    /**
     * Return true if the given locale is in the supported locale list.
     *
     * @param locale Check if this locale is supported
     * @see supportedLocales
     * */
    @Synchronized
    fun supportedLocale(locale: Locale): Boolean =
            with(supportedLocales.get()) {
                this == null
                        || isEmpty()
                        || any { AppHelpfulLocaleUtil.equals(locale, it) }
            }

    /**
     * Reset app locale to system best matched locale.
     * @param locales A list of [Locale] for matching. Default values is [supportedLocales]
     * @see supportedLocales
     * */
    fun resetLocaleToSystemBestMatched(locales: List<Locale> = supportedLocales.get()!!) {
        appLocale.set(AppHelpfulLocaleUtil.getDefaultLangFromSystemSetting(locales))
    }

    fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    companion object : SingletonHolder1<Application, SotwtmSupportLib>(::SotwtmSupportLib) {
        /**
         * Set the default supported locales list.
         * Empty list means supported all locales.
         * */
        @JvmStatic
        var defaultSupportedLocales: List<Locale> = emptyList()
            @Synchronized
            set(value) {
                field = value.map { AppHelpfulLocaleUtil.unify(it) }
            }

        @JvmStatic
        var enableDaggerErrorLog = true

        const val DEFAULT_SHARED_PREF_FILE = "sotwtm-support-lib"
        const val PREF_KEY_APP_LOCALE = "AppLocale"
        const val PREF_KEY_SUPPORTED_LOCALES = "SupportedLocales"
        const val SEPARATOR_LOCALE = ","
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