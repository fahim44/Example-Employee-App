package com.fahim.example_employee_app.di

import com.fahim.example_employee_app.ui.activity.AddOrEditActivity
import com.fahim.example_employee_app.ui.activity.DetailActivity
import com.fahim.example_employee_app.ui.activity.LauncherActivity
import com.fahim.example_employee_app.ui.fragment.ListFragment
import com.fahim.example_employee_app.ui.fragment.RemoveListFragment
import com.fahim.example_employee_app.ui.fragment.SearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    DatabaseModule::class,
    RetrofitModule::class,
    ViewModelModule::class])
interface AppComponent{

    fun inject(target: LauncherActivity)

    fun inject(target: ListFragment)

    fun inject(target: SearchFragment)

    fun inject(target: RemoveListFragment)

    fun inject(target: DetailActivity)

    fun inject(target: AddOrEditActivity)
}