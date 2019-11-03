package com.fahim.example_employee_app


import com.fahim.example_employee_app.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class EmployeeApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }


}