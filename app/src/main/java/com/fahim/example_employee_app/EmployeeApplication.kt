package com.fahim.example_employee_app

import android.app.Application
import com.fahim.example_employee_app.dagger.AppComponent
import com.fahim.example_employee_app.dagger.AppModule
import com.fahim.example_employee_app.dagger.DaggerAppComponent
import com.fahim.example_employee_app.dagger.RetrofitModule


class EmployeeApplication : Application() {
    lateinit var component: AppComponent

    private fun initDagger(app: EmployeeApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .retrofitModule(RetrofitModule("http://dummy.restapiexample.com/api/v1/"))
            .build()


    override fun onCreate() {
        super.onCreate()
        component = initDagger(this)
    }

}