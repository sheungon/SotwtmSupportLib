package com.sotwtm.support.rx

import android.content.SharedPreferences
import androidx.databinding.ObservableLong

/**
 * For observing [Long] value in [SharedPreferences]
 * @author sheungon
 * */
class ObservableSharedPreferencesLong(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor,
    private val preferenceKey: String,
    private val defaultValue: Long
) : ObservableLong() {

    private val sharedPreferencesListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == preferenceKey) {
                notifyChange()
            }
        }
    private var registeredListener = true

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

    @Synchronized
    fun restartPrefListener() {
        if (registeredListener) return
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
        registeredListener = true
    }

    @Synchronized
    fun endPrefListener() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener)
        registeredListener = false
    }

    @Synchronized
    override fun get(): Long =
        sharedPreferences.getLong(preferenceKey, defaultValue)

    @Synchronized
    override fun set(value: Long) {
        if (get() == value) {
            return
        }

        editor.putLong(preferenceKey, value)
        editor.apply()
        notifyChange()
    }
}