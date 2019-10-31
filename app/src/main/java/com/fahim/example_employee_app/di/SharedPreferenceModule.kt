package com.fahim.example_employee_app.di

import android.content.Context
import com.fahim.example_employee_app.util.SharedPreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferenceModule {
    @Provides
    @Singleton
    fun providePreference(context:Context) = SharedPreference(context)
}