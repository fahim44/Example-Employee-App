package com.fahim.example_employee_app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fahim.example_employee_app.di.anotation.ViewModelKey
import com.fahim.example_employee_app.viewmodel.*


import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
