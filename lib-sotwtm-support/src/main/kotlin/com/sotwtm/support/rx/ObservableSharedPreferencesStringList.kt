package com.sotwtm.support.rx

import android.content.SharedPreferences
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList

abstract class ObservableSharedPreferencesList<T>(
    sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor,
    private val preferenceKey: String
) : ObservableArrayList<T>() {

    private val changedCallback = OnObservableListChangedCallback()

    init {
        sharedPreferences.getString(preferenceKey, null)
            ?.runCatching {
                stringToList(this)
            }?.getOrNull()
            ?.let { addAll(it) }

        addOnListChangedCallback(changedCallback)
    }

    abstract fun listToString(list: ObservableList<T>): String

    abstract fun stringToList(string: String?): List<T>?

    private inner class OnObservableListChangedCallback :
        ObservableList.OnListChangedCallback<ObservableList<T>>() {

        override fun onChanged(sender: ObservableList<T>) {
            updatePref(sender)
        }

        override fun onItemRangeChanged(
            sender: ObservableList<T>,
            positionStart: Int,
            itemCount: Int
        ) {
            updatePref(sender)
        }

        override fun onItemRangeInserted(
            sender: ObservableList<T>,
            positionStart: Int,
            itemCount: Int
        ) {
            updatePref(sender)
        }

        override fun onItemRangeMoved(
            sender: ObservableList<T>,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
            updatePref(sender)
        }

        override fun onItemRangeRemoved(
            sender: ObservableList<T>,
            positionStart: Int,
            itemCount: Int
        ) {
            updatePref(sender)
        }

        private fun updatePref(sender: ObservableList<T>) {
            if (sender.isEmpty()) {
                editor.remove(preferenceKey)
            } else {
                editor.putString(preferenceKey, listToString(sender))
            }
            editor.apply()
        }
    }
}