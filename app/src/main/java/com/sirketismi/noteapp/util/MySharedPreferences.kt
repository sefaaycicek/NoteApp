package com.sirketismi.noteapp.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface MySharedPreferencesInterface {

    fun saveString(key : String, value : String)
    fun getString(key : String, defaultValue : String) : String

    fun saveBoolean(key : String, value : Boolean)
    fun getBoolean(key : String) : Boolean

}
class MySharedPreferences @Inject constructor(@ApplicationContext private val context : Context) : MySharedPreferencesInterface {

    private val sharedPreferences : SharedPreferences = context.getSharedPreferences("my_pref", Context.MODE_PRIVATE)
    override fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    override fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value)
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }
}