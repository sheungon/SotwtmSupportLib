package com.sotwtm.support.util.locale

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import java.util.*


/**
 * Locale Util for setting app language.
 * Reference, https://stackoverflow.com/questions/40221711/android-context-getresources-updateconfiguration-deprecated
 * @author sheunogn
 */

object AppHelpfulLocaleUtil {

    /**
     * Get the best matching [Locale] for the current system setting.
     * @param supportedLocales A list of available [Locale] supported. The returned [Locale] will
     * be one of the given value in the list. Given null means all languages are supported.
     * */
    fun getDefaultLangFromSystemSetting(supportedLocales: List<Locale>?): Locale {

        val config = Resources.getSystem().configuration
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getSystemLocale(config, supportedLocales)
        } else {
            getSystemLocaleLegacy(config, supportedLocales)
        }
    }

    /**
     * @param fuzzy true to allow fuzzy match
     * */
    fun equals(
        left: Locale,
        right: Locale,
        fuzzy: Boolean = true
    ): Boolean =
        if (fuzzy) {
            val unifiedLeft = unify(left)
            val unifiedRight = unify(right)
            unifiedLeft.language.toUpperCase() == unifiedRight.language.toUpperCase() &&
                    (unifiedLeft.language.toUpperCase() != "ZH" ||
                            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                                    (unifiedLeft.script.isEmpty() || unifiedRight.script.isEmpty() ||
                                            unifiedLeft.script.toUpperCase() == unifiedRight.script.toUpperCase())) ||
                            zhLangConvert(unifiedLeft.country) == zhLangConvert(unifiedRight.country))
        } else
            left == right

    /**
     * Covert locale to unified value
     * @param locale A locale going to be unified
     * @return A unified [Locale]
     * */
    fun unify(locale: Locale): Locale =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (locale.script.isEmpty()) {
                if (locale.language.toUpperCase() == "ZH") {
                    val localeBuilder = Locale.Builder().setLanguage(locale.language).setRegion(locale.country)
                    when (locale.country.toUpperCase()) {
                        "CN" -> localeBuilder.setScript("hans")
                        else -> localeBuilder.setScript("hant")
                    }
                    localeBuilder.build()
                } else locale
            } else locale
        } else locale

    private fun zhLangConvert(country: String): String =
        when (country.toUpperCase()) {
            "HK" -> "TW"
            "MO" -> "TW"
            else -> country.toUpperCase()
        }

    @Suppress("DEPRECATION")
    private fun getSystemLocaleLegacy(
        config: Configuration,
        supportedLocales: List<Locale>?
    ): Locale =
        if (supportedLocales == null || supportedLocales.isEmpty())
            config.locale
        else
            find(supportedLocales, config.locale)
                ?: findFirstMatchedLanguage(supportedLocales, config.locale)
                ?: supportedLocales[0]

    @TargetApi(Build.VERSION_CODES.N)
    private fun getSystemLocale(
        config: Configuration,
        supportedLocales: List<Locale>?
    ): Locale {
        if (supportedLocales == null || supportedLocales.isEmpty())
            return config.locales[0]
        else {
            (0 until config.locales.size())
                .mapNotNull { find(supportedLocales, config.locales[it]) }
                .forEach { return it }
        }
        return supportedLocales[0]
    }

    private fun find(
        supportedLocales: List<Locale>,
        target: Locale,
        fuzzy: Boolean = true
    ): Locale? {
        supportedLocales.forEach { locale ->
            if (equals(locale, target, fuzzy)) {
                return locale
            }
        }
        return null
    }

    private fun findFirstMatchedLanguage(
        supportedLocales: List<Locale>,
        target: Locale
    ): Locale? =
        (0 until supportedLocales.size)
            .firstOrNull { supportedLocales[it].language == target.language }
            ?.let { supportedLocales[it] }
}

@Suppress("DEPRECATION")
fun Context.setAppLocale(locale: Locale): Context {

    Locale.setDefault(locale)
    val applicationContext = applicationContext

    val config = resources.configuration
    val appConfig = applicationContext.resources.configuration
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)

            appConfig.locales = localeList
            config.locales = localeList
            appConfig.setLocale(locale)
            config.setLocale(locale)
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
            appConfig.setLocale(locale)
            config.setLocale(locale)
        }
        else -> {
            appConfig.locale = locale
            config.locale = locale
        }
    }

    applicationContext.resources.updateConfiguration(appConfig, applicationContext.resources.displayMetrics)

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        createConfigurationContext(config)
    } else {
        resources.updateConfiguration(config, resources.displayMetrics)
        ContextWrapper(this)
    }
}