package com.sotwtm.support

import android.content.Context
import android.content.SharedPreferences
import com.sotwtm.support.scope.LibScope
import dagger.Module
import dagger.Provides

@Module
internal class SotwtmSupportBaseModule {

    @LibScope
    @Provides
    fun sharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences(DEFAULT_SHARED_PREF_FILE, Context.MODE_PRIVATE)

    @LibScope
    @Provides
    fun editor(sharedPreferences: SharedPreferences): SharedPreferences.Editor =
            sharedPreferences.edit()

    companion object {
        const val DEFAULT_SHARED_PREF_FILE = "sotwtm-support-lib"
    }
}