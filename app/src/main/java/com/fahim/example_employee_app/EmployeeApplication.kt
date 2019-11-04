package com.fahim.example_employee_app


import com.fahim.example_employee_app.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class EmployeeApplication : DaggerApplication() {


    override fun onCreate() {
        super.onCreate()
        setLogger()
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }


    private fun setLogger() {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            } })
    }
}