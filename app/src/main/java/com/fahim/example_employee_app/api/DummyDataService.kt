package com.fahim.example_employee_app.api

import com.fahim.example_employee_app.model.Employee
import retrofit2.Call
import retrofit2.http.GET

interface DummyDataService {

    @GET("employees")
    fun getDummyEmployeesData() : Call<List<Employee>>
}