package com.fahim.example_employee_app.dagger

import com.fahim.example_employee_app.ui.activities.MainActivity
import com.fahim.example_employee_app.viewmodels.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    SharedPreferenceModule::class,
    DatabaseModule::class,
    RepositoryModule::class,
    RetrofitModule::class,
    ViewModelModule::class])
interface AppComponent{


    fun inject(target: MainActivity)

    fun inject(target: ListViewModel)

    fun inject(target: SearchViewModel)

    fun inject(target: RemoveListViewModel)

    fun inject(target: DetailViewModel)

    fun inject(target: AddOrEditViewModel)

}