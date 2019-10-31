package com.fahim.example_employee_app.di

import com.fahim.example_employee_app.repository.EmployeeRepository
import com.fahim.example_employee_app.api.DummyDataService
import com.fahim.example_employee_app.db.EmployeeDao
import com.fahim.example_employee_app.util.SharedPreference
import com.fahim.example_employee_app.util.TaskUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(dao:EmployeeDao, dataService:DummyDataService,
                          preference: SharedPreference,
                          taskUtils: TaskUtils)
            = EmployeeRepository(dao,dataService,preference,taskUtils)
}