package com.fahim.example_employee_app.di.appModule

import com.fahim.example_employee_app.preference.SharedPreference
import com.fahim.example_employee_app.preference.SharedPreferenceImpl
import com.fahim.example_employee_app.repository.EmployeeRepository
import com.fahim.example_employee_app.repository.EmployeeRepositoryImpl
import com.fahim.example_employee_app.util.TaskUtils
import com.fahim.example_employee_app.util.TaskUtilsImpl
import dagger.Binds
import dagger.Module

@Module
interface AppBindingModule {
    @Binds
    fun bindTaskUtils(utilsImpl: TaskUtilsImpl) : TaskUtils

    @Binds
    fun bindSharedPreference(preferenceImpl: SharedPreferenceImpl):SharedPreference

    @Binds
    fun bindRepository(repositoryImpl: EmployeeRepositoryImpl) : EmployeeRepository
}