package com.fahim.example_employee_app.di.fragmentModule

import androidx.lifecycle.ViewModel
import com.fahim.example_employee_app.di.anotation.ViewModelKey
import com.fahim.example_employee_app.viewmodel.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchFragmentModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(userViewModel: SearchViewModel): ViewModel
}