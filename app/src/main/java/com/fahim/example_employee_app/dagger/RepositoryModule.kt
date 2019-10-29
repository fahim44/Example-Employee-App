package com.fahim.example_employee_app.dagger

import com.fahim.example_employee_app.repositories.EmployeeRepository
import com.fahim.example_employee_app.retrofit.DummyDataService
import com.fahim.example_employee_app.room.EmployeeDao
import com.fahim.example_employee_app.utils.SharedPreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(dao:EmployeeDao, dataService:DummyDataService, preference: SharedPreference) = EmployeeRepository(dao,dataService,preference)
}