package com.fahim.example_employee_app.dagger

import com.fahim.example_employee_app.repositories.EmployeeRepository
import com.fahim.example_employee_app.room.EmployeeDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(database: EmployeeDatabase) : EmployeeRepository {
        return EmployeeRepository(database.employeeDao())
    }
}