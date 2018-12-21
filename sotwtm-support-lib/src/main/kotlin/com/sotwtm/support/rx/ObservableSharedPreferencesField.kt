package com.sotwtm.support.rx

import android.content.SharedPreferences
import android.databinding.ObservableField
import com.sotwtm.util.Log

abstract class ObservableSharedPreferencesField<T>(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor,
    private val preferenceKey: String
) : ObservableField<T>() {

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

    abstract fun valueToString(value: T): String?

    abstract fun stringToValue(string: String?): T

    @Synchronized
    override fun get(): T? = try {
        stringToValue(sharedPreferences.getString(preferenceKey, null))
    } catch (th: Throwable) {
        Log.e("Error on convert String to value. Failed to get.", th)
        null
    }

    @Synchronized
    override fun set(value: T) {
        if (get() == value) {
            return
        }

        try {
            valueToString(value)?.let {
                editor.putString(preferenceKey, it)
            } ?: run {
                editor.remove(preferenceKey)
            }
            editor.apply()
            notifyChange()
        } catch (th: Throwable) {
            Log.e("Error on convert value to String. Failed to set.", th)
        }
    }
}