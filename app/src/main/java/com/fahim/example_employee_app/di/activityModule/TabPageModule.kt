package com.fahim.example_employee_app.di.activityModule

import com.fahim.example_employee_app.di.anotation.ChildFragmentScoped
import com.fahim.example_employee_app.di.fragmentModule.*
import com.fahim.example_employee_app.ui.fragment.*
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

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [DetailFragmentModule::class])
    internal abstract fun detail(): DetailFragment

    @ChildFragmentScoped
    @ContributesAndroidInjector(modules = [AddOrEditFragmentModule::class])
    internal abstract fun addOrEdit(): AddOrEditFragment
}