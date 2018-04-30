package com.sotwtm.support.util

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import java.util.*


/**
 * Language Util for set app language.
 * Reference, https://stackoverflow.com/questions/40221711/android-context-getresources-updateconfiguration-deprecated
 * @author John
 */

object AppHelpfulLanguageUtil {

    @Suppress("DEPRECATION")
    fun setAppLocale(context: Context,
                     locale: Locale): Context {

        Locale.setDefault(locale)

        val config = context.resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            config.locale = locale
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context.createConfigurationContext(config)
        } else {
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            ContextWrapper(context)
        }
    }

    /**
     * Get the best matching [Locale] for the current system setting.
     * @param context App context
     * @param availableLangList A list of available [Locale] supported. The returned [Locale] will
     * be one of the given value in the list. Given null means all languages are available.
     * */
    fun getDefaultLangFromSystemSetting(context: Context,
                                        availableLangList: List<Locale>?): Locale {

        val config = context.resources.configuration
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getSystemLocale(config, availableLangList)
        } else {
            getSystemLocaleLegacy(config, availableLangList)
        }
    }

    /**
     * @param fuzzy true to allow fuzzy match
     * */
    fun equals(left: Locale,
               right: Locale,
               fuzzy: Boolean = true): Boolean =
            if (fuzzy)
                left.language.toUpperCase() == right.language.toUpperCase() &&
                        (left.country.toUpperCase() == right.country.toUpperCase() ||
                                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                                        left.script.toUpperCase() == right.script.toUpperCase()))
            else
                left == right

    @Suppress("DEPRECATION")
    private fun getSystemLocaleLegacy(config: Configuration,
                                      availableLangList: List<Locale>?): Locale =
            if (availableLangList == null || availableLangList.isEmpty())
                config.locale
            else
                find(availableLangList, config.locale)
                        ?: findFirstMatchedLanguage(availableLangList, config.locale)
                        ?: availableLangList[0]

    @TargetApi(Build.VERSION_CODES.N)
    private fun getSystemLocale(config: Configuration,
                                availableLangList: List<Locale>?): Locale {
        if (availableLangList == null || availableLangList.isEmpty())
            return config.locales[0]
        else {
            (0 until config.locales.size())
                    .mapNotNull { find(availableLangList, config.locales[it]) }
                    .forEach { return it }
        }
        return availableLangList[0]
    }

    private fun find(availableLangList: List<Locale>,
                     target: Locale,
                     fuzzy: Boolean = true): Locale? {
        availableLangList.forEach { locale ->
            if (equals(locale, target, fuzzy)) {
                return locale
            }
        }
        return null
    }

    private fun findFirstMatchedLanguage(_availableLangList: List<Locale>,
                                         target: Locale): Locale? =
            (0 until _availableLangList.size)
                    .firstOrNull { _availableLangList[it].language == target.language }
                    ?.let { _availableLangList[it] }
}
