package com.fahim.example_employee_app.di.activityModule

import com.fahim.example_employee_app.di.anotation.ChildFragmentScoped
import com.fahim.example_employee_app.di.fragmentModule.*
import com.fahim.example_employee_app.ui.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
interface TabPageModule {

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [ListFragmentModule::class])
    fun list(): ListFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
    fun search(): SearchFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [RemoveListFragmentModule::class])
    fun remove(): RemoveListFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [DetailFragmentModule::class])
    fun detail(): DetailFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [AddOrEditFragmentModule::class])
    fun addOrEdit(): AddOrEditFragment
}