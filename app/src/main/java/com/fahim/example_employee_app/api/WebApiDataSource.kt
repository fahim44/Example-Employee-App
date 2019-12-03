package com.fahim.example_employee_app.api

import com.fahim.example_employee_app.model.Employee

interface WebApiDataSource {
    fun retrieveDummyData(): Pair<Boolean,List<Employee>?>
}