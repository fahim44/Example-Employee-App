package com.fahim.example_employee_app.dagger

import com.fahim.example_employee_app.viewmodels.*
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

    fun inject(target: DetailViewModel)
}