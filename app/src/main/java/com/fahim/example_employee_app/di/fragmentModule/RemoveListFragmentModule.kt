package com.fahim.example_employee_app.di.fragmentModule

import androidx.lifecycle.ViewModel
import com.fahim.example_employee_app.di.anotation.ViewModelKey
import com.fahim.example_employee_app.viewmodel.RemoveListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RemoveListFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(RemoveListViewModel::class)
    abstract fun bindRemoveListViewModel(userViewModel: RemoveListViewModel): ViewModel
}