package com.fahim.example_employee_app

import android.app.Application
import com.fahim.example_employee_app.dagger.AppComponent
import com.fahim.example_employee_app.dagger.AppModule
import com.fahim.example_employee_app.dagger.DaggerAppComponent


class EmployeeApplication : Application() {
    lateinit var component: AppComponent

    private fun initDagger(app: EmployeeApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()


    override fun onCreate() {
        super.onCreate()
        component = initDagger(this)
    }

}