package com.fahim.example_employee_app.dagger

import android.content.Context
import com.fahim.example_employee_app.utils.SharedPreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferenceModule {
    @Provides
    @Singleton
    fun providePreference(context:Context) = SharedPreference(context)
}