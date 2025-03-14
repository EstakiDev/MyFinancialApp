package dev.estaki.domain.sharedPrefrence

interface PreferenceHelper {

    fun saveString(key: String,value: String)
    fun getString(key: String,defValue: String = ""): String
    fun saveInt(key: String, value: Int)
    fun getInt(key: String, defValue: Int = 0): Int
    fun saveLong(key: String, value: Long)
    fun getLong(key: String, defValue: Long = 0L): Long
    fun saveBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defValue: Boolean = false): Boolean
    fun clearPreferences()
}