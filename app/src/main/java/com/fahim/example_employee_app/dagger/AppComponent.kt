package com.fahim.example_employee_app.dagger

import com.fahim.example_employee_app.viewmodels.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    SharedPreferenceModule::class,
    DatabaseModule::class,
    RepositoryModule::class])
interface AppComponent{
    fun inject(target: MainActivityViewModel)
}