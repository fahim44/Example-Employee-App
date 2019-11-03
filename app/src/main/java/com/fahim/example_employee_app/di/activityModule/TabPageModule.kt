package com.fahim.example_employee_app.di.activityModule

import com.fahim.example_employee_app.di.anotation.ChildFragmentScoped
import com.fahim.example_employee_app.di.fragmentModule.ListFragmentModule
import com.fahim.example_employee_app.di.fragmentModule.RemoveListFragmentModule
import com.fahim.example_employee_app.di.fragmentModule.SearchFragmentModule
import com.fahim.example_employee_app.ui.fragment.ListFragment
import com.fahim.example_employee_app.ui.fragment.RemoveListFragment
import com.fahim.example_employee_app.ui.fragment.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
internal abstract class TabPageModule {

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [ListFragmentModule::class])
    internal abstract fun list(): ListFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
    internal abstract fun search(): SearchFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [RemoveListFragmentModule::class])
    internal abstract fun remove(): RemoveListFragment
}