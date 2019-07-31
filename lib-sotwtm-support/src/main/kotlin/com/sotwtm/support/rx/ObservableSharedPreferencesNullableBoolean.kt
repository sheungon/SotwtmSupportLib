package com.sotwtm.support.rx

import android.content.SharedPreferences

/**
 * For observing [Boolean]? value in [SharedPreferences]
 * @author sheungon
 * */
class ObservableSharedPreferencesNullableBoolean(
    sharedPreferences: SharedPreferences,
    editor: SharedPreferences.Editor,
    preferenceKey: String,
    private val defaultValue: Boolean? = null
) : ObservableSharedPreferencesField<Boolean?>(
    sharedPreferences,
    editor,
    preferenceKey
) {
    override fun valueToString(value: Boolean?): String? = value?.toString()

    override fun stringToValue(containsValue: Boolean, string: String?): Boolean? =
        if (containsValue) string?.toBoolean()
        else defaultValue
}