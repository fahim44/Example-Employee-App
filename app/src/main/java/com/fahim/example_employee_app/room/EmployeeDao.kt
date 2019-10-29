package com.fahim.example_employee_app.room

import androidx.paging.DataSource
import androidx.room.*
import com.fahim.example_employee_app.models.Employee
import androidx.lifecycle.LiveData



@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg employees : Employee)

    @Delete
    fun delete(employee: Employee)

    @Update
    fun update(employee: Employee)

    @Query("SELECT * FROM employee ORDER BY id ASC")
    fun getAllEmployees(): DataSource.Factory<Int, Employee>

    @Query("SELECT * FROM employee WHERE employee_name LIKE :name ORDER BY employee_rating DESC")
    fun employeesSortByName(name : String): DataSource.Factory<Int, Employee>

    @Query("SELECT COUNT(id) FROM employee")
    fun getRowCount(): LiveData<Int>

    @Query("SELECT * FROM employee")
    fun getAll(): List<Employee>

}