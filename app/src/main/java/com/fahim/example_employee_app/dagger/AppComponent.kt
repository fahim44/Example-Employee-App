package com.fahim.example_employee_app.dagger

import com.fahim.example_employee_app.viewmodels.AddRemoveViewModel
import com.fahim.example_employee_app.viewmodels.ListViewModel
import com.fahim.example_employee_app.viewmodels.MainActivityViewModel
import com.fahim.example_employee_app.viewmodels.SearchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    SharedPreferenceModule::class,
    DatabaseModule::class,
    RepositoryModule::class,
    RetrofitModule::class])
interface AppComponent{
    fun inject(target: MainActivityViewModel)

    fun inject(target: ListViewModel)

    fun inject(target: SearchViewModel)

    fun inject(target: AddRemoveViewModel)
}