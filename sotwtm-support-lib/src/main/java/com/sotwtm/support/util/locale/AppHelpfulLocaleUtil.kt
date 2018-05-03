package com.sotwtm.support.util.locale

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import java.util.*


/**
 * Locale Util for setting app language.
 * Reference, https://stackoverflow.com/questions/40221711/android-context-getresources-updateconfiguration-deprecated
 * @author John
 */

object AppHelpfulLocaleUtil {

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
     * @param supportedLocales A list of available [Locale] supported. The returned [Locale] will
     * be one of the given value in the list. Given null means all languages are supported.
     * */
    fun getDefaultLangFromSystemSetting(context: Context,
                                        supportedLocales: List<Locale>?): Locale {

        val config = context.resources.configuration
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getSystemLocale(config, supportedLocales)
        } else {
            getSystemLocaleLegacy(config, supportedLocales)
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
                                      supportedLocales: List<Locale>?): Locale =
            if (supportedLocales == null || supportedLocales.isEmpty())
                config.locale
            else
                find(supportedLocales, config.locale)
                        ?: findFirstMatchedLanguage(supportedLocales, config.locale)
                        ?: supportedLocales[0]

    @TargetApi(Build.VERSION_CODES.N)
    private fun getSystemLocale(config: Configuration,
                                supportedLocales: List<Locale>?): Locale {
        if (supportedLocales == null || supportedLocales.isEmpty())
            return config.locales[0]
        else {
            (0 until config.locales.size())
                    .mapNotNull { find(supportedLocales, config.locales[it]) }
                    .forEach { return it }
        }
        return supportedLocales[0]
    }

    private fun find(supportedLocales: List<Locale>,
                     target: Locale,
                     fuzzy: Boolean = true): Locale? {
        supportedLocales.forEach { locale ->
            if (equals(locale, target, fuzzy)) {
                return locale
            }
        }
        return null
    }

    private fun findFirstMatchedLanguage(supportedLocales: List<Locale>,
                                         target: Locale): Locale? =
            (0 until supportedLocales.size)
                    .firstOrNull { supportedLocales[it].language == target.language }
                    ?.let { supportedLocales[it] }
}
