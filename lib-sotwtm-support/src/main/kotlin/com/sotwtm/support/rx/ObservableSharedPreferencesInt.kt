package com.sotwtm.support.rx

import android.content.SharedPreferences
import androidx.databinding.ObservableInt

/**
 * For observing [Int] value in [SharedPreferences]
 * @author sheungon
 * */
class ObservableSharedPreferencesInt(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor,
    private val preferenceKey: String,
    private val defaultValue: Int
) : ObservableInt() {

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
    override fun get(): Int =
        sharedPreferences.getInt(preferenceKey, defaultValue)

    @Synchronized
    override fun set(value: Int) {
        if (get() == value) {
            return
        }

        editor.putInt(preferenceKey, value)
        editor.apply()
        notifyChange()
    }
}