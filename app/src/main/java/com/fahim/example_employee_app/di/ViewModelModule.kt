package com.fahim.example_employee_app.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fahim.example_employee_app.viewmodel.*


import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LauncherViewModel::class)
    abstract fun bindLauncherViewModel(userViewModel: LauncherViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun bindListViewModel(userViewModel: ListViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(RemoveListViewModel::class)
    abstract fun bindRemoveListViewModel(userViewModel: RemoveListViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(userViewModel: DetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddOrEditViewModel::class)
    abstract fun bindAddOrEditViewModel(userViewModel: AddOrEditViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(userViewModel: SearchViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
