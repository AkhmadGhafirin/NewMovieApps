package com.cascer.madesubmission2.manager

import android.content.Context
import com.cascer.madesubmission2.utils.AUTH_PREFERENCES

class StorageManager(ctx: Context) {

    private var context: Context? = ctx

    fun save(key: String, value: String) {
        val preferences = context!!.getSharedPreferences(
            AUTH_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun save(key: String, value: Int) {
        val preferences = context!!.getSharedPreferences(
            AUTH_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun save(key: String, value: Boolean) {
        val preferences = context!!.getSharedPreferences(
            AUTH_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getString(key: String): String? {
        val preferences = context!!.getSharedPreferences(
            AUTH_PREFERENCES,
            Context.MODE_PRIVATE
        )
        return preferences.getString(key, "")
    }

    fun getString(key: String, defValue: String): String? {
        val preferences = context!!.getSharedPreferences(
            AUTH_PREFERENCES,
            Context.MODE_PRIVATE
        )
        return preferences.getString(key, defValue)
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        val preferences = context!!.getSharedPreferences(
            AUTH_PREFERENCES,
            Context.MODE_PRIVATE
        )
        return preferences.getBoolean(key, defValue)
    }

    fun getBoolean(key: String): Boolean {
        val preferences = context!!.getSharedPreferences(
            AUTH_PREFERENCES,
            Context.MODE_PRIVATE
        )
        return preferences.getBoolean(key, false)
    }

    fun getInt(key: String): Int {
        val preferences = context!!.getSharedPreferences(
            AUTH_PREFERENCES,
            Context.MODE_PRIVATE
        )
        return preferences.getInt(key, 0)
    }

    fun remove(key: String) {
        save(key, "")
    }

    fun clearAll() {
        val preferences = context!!.getSharedPreferences(
            AUTH_PREFERENCES,
            Context.MODE_PRIVATE
        )
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}