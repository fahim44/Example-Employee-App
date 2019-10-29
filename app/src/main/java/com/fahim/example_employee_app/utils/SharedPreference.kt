package com.fahim.example_employee_app.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit


class SharedPreference (context : Context) {
    companion object{
        private const val PREFERENCE_NAME = "example_employee_app"
        private const val DUMMY_DATA_DOWNLOADED = "dummy_data_downloaded"
    }


    private val preference : SharedPreferences


    init {
        preference = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)
    }


// Check is initial Dummy data downloaded from api & stored in the local db or not
    fun isInitDataLoaded() : Boolean {
        return preference.getBoolean(DUMMY_DATA_DOWNLOADED, false)
    }


    fun initDataLoaded(value:Boolean){
        preference.edit { putBoolean(DUMMY_DATA_DOWNLOADED, value) }
    }
}