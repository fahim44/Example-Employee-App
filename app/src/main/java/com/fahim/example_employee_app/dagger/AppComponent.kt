package com.fahim.example_employee_app.dagger

import com.fahim.example_employee_app.ui.activities.AddOrEditActivity
import com.fahim.example_employee_app.ui.activities.DetailActivity
import com.fahim.example_employee_app.ui.activities.MainActivity
import com.fahim.example_employee_app.ui.fragments.ListFragment
import com.fahim.example_employee_app.ui.fragments.RemoveListFragment
import com.fahim.example_employee_app.ui.fragments.SearchFragment
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

    fun inject(target: ListFragment)

    fun inject(target: SearchFragment)

    fun inject(target: RemoveListFragment)

    fun inject(target: DetailActivity)

    fun inject(target: AddOrEditActivity)
}