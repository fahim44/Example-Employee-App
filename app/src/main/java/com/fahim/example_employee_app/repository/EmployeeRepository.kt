package com.fahim.example_employee_app.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.fahim.example_employee_app.model.Employee


interface EmployeeRepository {

    fun isDummyDataLoaded() : Boolean

    fun setDummyDataLoaded()

    fun getEmployee(uid:Int) : LiveData<Employee>

    fun getAllEmployees() : LiveData<PagedList<Employee>>

    fun getSearchedEmployeeList(name:String) : LiveData<PagedList<Employee>>

    suspend fun insertEmployees(employees: List<Employee>) : Boolean

    suspend fun updateEmployeeRating(id: Int, rating : Float) : Boolean

    suspend fun updateEmployee(employee: Employee) : Boolean

    suspend fun deleteEmployee(employee: Employee) : Boolean

    suspend fun getDummyDataFromServerAndLoadToLocalDB() : Boolean

    suspend fun retrieveDataFromServer(): Pair<Boolean,List<Employee>>
}