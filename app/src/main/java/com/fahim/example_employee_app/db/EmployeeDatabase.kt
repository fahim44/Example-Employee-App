package com.fahim.example_employee_app.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.fahim.example_employee_app.model.Employee

@Database(entities = [Employee::class], version = 1)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract fun employeeDao() : EmployeeDao

    companion object{
        const val DATABASE_NAME = "employee_database"
    }
}