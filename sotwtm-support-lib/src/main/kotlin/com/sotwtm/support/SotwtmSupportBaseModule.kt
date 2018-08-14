package com.sotwtm.support

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
internal class SotwtmSupportBaseModule {

    @Provides
    fun sharedPreferences(context: Context) =
            context.getSharedPreferences(DEFAULT_SHARED_PREF_FILE, Context.MODE_PRIVATE)

    @Provides
    fun editor(sharedPreferences: SharedPreferences) =
            sharedPreferences.edit()

    companion object {
        const val DEFAULT_SHARED_PREF_FILE = "sotwtm-support-lib"
    }
}