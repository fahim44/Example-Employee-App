package com.fahim.example_employee_app.di.activityModule

import androidx.lifecycle.ViewModel
import com.fahim.example_employee_app.di.anotation.ViewModelKey
import com.fahim.example_employee_app.viewmodel.LauncherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LauncherModule {
    @Binds
    @IntoMap
    @ViewModelKey(LauncherViewModel::class)
    abstract fun bindLauncherViewModel(userViewModel: LauncherViewModel): ViewModel
}