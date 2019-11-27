package com.fahim.example_employee_app.db

import androidx.paging.DataSource
import androidx.room.*
import com.fahim.example_employee_app.model.Employee
import androidx.lifecycle.LiveData



@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg employees : Employee) : List<Long>

    @Delete
    fun delete(employee: Employee) : Int

    @Update
    fun update(employee: Employee) : Int

    @Query("SELECT * FROM employee ORDER BY id ASC")
    fun getAllEmployees(): DataSource.Factory<Int, Employee>

    @Query("SELECT * FROM employee WHERE employee_name LIKE :name ORDER BY employee_rating DESC")
    fun employeesSortByName(name : String): DataSource.Factory<Int, Employee>

    @Query("SELECT * FROM employee WHERE uid = :uid")
    fun getEmployee(uid:Int): LiveData<Employee>

    @Query("UPDATE employee SET employee_rating = :rating WHERE uid = :id")
    fun updateRating(id:Int, rating: Float) : Int
}