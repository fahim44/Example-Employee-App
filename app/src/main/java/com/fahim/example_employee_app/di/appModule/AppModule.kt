package com.fahim.example_employee_app.di.appModule

import android.app.Application
import android.content.Context
import com.fahim.example_employee_app.EmployeeApplication
import dagger.Module
import dagger.Provides

@Module(includes = [DatabaseModule::class,
    RetrofitModule::class,
    AppBindingModule::class])
class AppModule {
    @Provides
    fun provideContext(app:EmployeeApplication): Context = app.applicationContext

    @Provides
    fun provideApplication(app:EmployeeApplication): Application = app
}
