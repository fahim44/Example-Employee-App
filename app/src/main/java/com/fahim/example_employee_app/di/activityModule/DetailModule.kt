package com.fahim.example_employee_app.di.activityModule

import androidx.lifecycle.ViewModel
import com.fahim.example_employee_app.di.anotation.ViewModelKey
import com.fahim.example_employee_app.viewmodel.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailModule {
    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(userViewModel: DetailViewModel): ViewModel
}