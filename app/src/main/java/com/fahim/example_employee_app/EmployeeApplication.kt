package com.fahim.example_employee_app


import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkerFactory
import com.fahim.example_employee_app.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import javax.inject.Inject


class EmployeeApplication : DaggerApplication(), Configuration.Provider {
    @Inject lateinit var workerFactory: WorkerFactory

    override fun onCreate() {
        super.onCreate()
        setLogger()
    }

    override fun getWorkManagerConfiguration()=
        Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .setWorkerFactory(workerFactory).build()


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