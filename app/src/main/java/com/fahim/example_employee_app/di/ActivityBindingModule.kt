package com.fahim.example_employee_app.di

import com.fahim.example_employee_app.di.activityModule.AddOrEditModule
import com.fahim.example_employee_app.di.activityModule.LauncherModule
import com.fahim.example_employee_app.di.activityModule.TabPageModule
import com.fahim.example_employee_app.di.anotation.ActivityScoped
import com.fahim.example_employee_app.ui.activity.AddOrEditActivity
import com.fahim.example_employee_app.ui.activity.LauncherActivity
import com.fahim.example_employee_app.ui.activity.TabPageActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
@Suppress("UNUSED")
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [LauncherModule::class])
    internal abstract fun launcherActivity(): LauncherActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [TabPageModule::class])
    internal abstract fun tabPageActivity(): TabPageActivity


    /*@ActivityScoped
    @ContributesAndroidInjector(modules = [DetailFragmentModule::class])
    internal abstract fun detailActivity(): DetailActivity*/


    @ActivityScoped
    @ContributesAndroidInjector(modules = [AddOrEditModule::class])
    internal abstract fun addOrEditActivity(): AddOrEditActivity
}