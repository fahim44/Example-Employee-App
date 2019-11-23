package com.fahim.example_employee_app.di.activityModule

import com.fahim.example_employee_app.di.ViewUtilsModule
import com.fahim.example_employee_app.di.anotation.ChildFragmentScoped
import com.fahim.example_employee_app.di.fragmentModule.*
import com.fahim.example_employee_app.ui.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
interface TabPageModule {

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [ListViewModelModule::class])
    fun list(): ListFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [SearchViewModelModule::class])
    fun search(): SearchFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [RemoveListFragmentModule::class,RemoveListViewModelModule::class,
        ViewUtilsModule::class])
    fun remove(): RemoveListFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [DetailViewModelModule::class])
    fun detail(): DetailFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [AddOrEditViewModelModule::class])
    fun addOrEdit(): AddOrEditFragment
}