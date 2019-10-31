package com.fahim.example_employee_app.di

import android.app.Application
import android.content.Context
import com.fahim.example_employee_app.util.TaskUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideTaskUtils(context: Context) = TaskUtils(context)
}
