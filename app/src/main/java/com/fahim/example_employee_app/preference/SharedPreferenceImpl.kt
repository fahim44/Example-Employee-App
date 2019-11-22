package com.fahim.example_employee_app.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceImpl @Inject constructor(context : Context) : SharedPreference {
    companion object{
        private const val PREFERENCE_NAME = "example_employee_app"
        private const val DUMMY_DATA_DOWNLOADED = "dummy_data_downloaded"
    }


    private val preference : SharedPreferences


    init {
        preference = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)
    }


    // Check is initial Dummy data downloaded from api & stored in the local db or not
    override fun isInitDataLoaded() : Boolean {
        return preference.getBoolean(DUMMY_DATA_DOWNLOADED, false)
    }


    override fun initDataLoaded(value:Boolean){
        preference.edit { putBoolean(DUMMY_DATA_DOWNLOADED, value) }
    }
}