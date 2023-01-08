package com.purabmodi.agrihelp.utility

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    val sharedPreferences = context.getSharedPreferences("AgriHelp", Context.MODE_PRIVATE)

    fun setLoggedIn(value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", value)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    fun setUserName(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.apply()
    }

    fun getUserName(): String {
        return sharedPreferences.getString("username", "Unkown User")!!
    }

}