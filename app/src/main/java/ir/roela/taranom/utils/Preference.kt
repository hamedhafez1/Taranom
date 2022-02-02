package ir.roela.taranom.utils

import android.content.Context
import android.content.SharedPreferences

class Preference(context: Context) {

    companion object {
        private lateinit var preferences: SharedPreferences
    }

    init {
        preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    enum class PType(val value: String = "") {
        InsertAppHashtag("insert_app_hashtag"),
        SubContentDesc("sub_content_desc")
    }

    /*fun isPreferenceExisted(type: PType): Boolean {
        val pref = type.value
        return preferences.contains(pref)
    }*/

    fun getBooleanPreference(type: PType): Boolean {
        return preferences.getBoolean(type.value, false)
    }

    fun getStringPreference(type: PType): String? {
        return preferences.getString(type.value, "")
    }

    fun setBooleanPreference(type: PType, status: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(type.value, status)
        editor.apply()
    }

    fun setStringPreference(type: PType, status: String?) {
        val editor = preferences.edit()
        editor.putString(type.value, status)
        editor.apply()
    }

}