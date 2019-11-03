package com.fahim.example_employee_app.di

import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.di.appModule.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    AppModule::class,
    ViewModelModule::class])
interface AppComponent : AndroidInjector<EmployeeApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: EmployeeApplication): AppComponent
    }
}