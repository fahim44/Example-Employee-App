package com.fahim.example_employee_app.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.fahim.example_employee_app.model.Employee

interface LocalDBDataSource {
    fun insertEmployees(employees : List<Employee?>?) : List<Long>?
    fun getAllEmployees() : DataSource.Factory<Int, Employee>
    fun employeesSortByName(name:String?) : DataSource.Factory<Int,Employee>
    fun getEmployee(uid:Int): LiveData<Employee>
    fun updateEmployee(employee: Employee?) : Boolean
    fun updateRating(id:Int, rating: Float) : Boolean
    fun deleteEmployee(employee: Employee?) : Boolean
}