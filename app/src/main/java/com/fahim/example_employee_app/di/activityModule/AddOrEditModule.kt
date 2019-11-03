package com.fahim.example_employee_app.di.activityModule

import androidx.lifecycle.ViewModel
import com.fahim.example_employee_app.di.anotation.ViewModelKey
import com.fahim.example_employee_app.viewmodel.AddOrEditViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddOrEditModule {
    @Binds
    @IntoMap
    @ViewModelKey(AddOrEditViewModel::class)
    abstract fun bindAddOrEditViewModel(userViewModel: AddOrEditViewModel): ViewModel
}