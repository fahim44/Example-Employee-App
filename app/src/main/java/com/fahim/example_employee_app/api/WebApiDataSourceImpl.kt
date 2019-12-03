package com.fahim.example_employee_app.api

import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.util.TaskUtils
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WebApiDataSourceImpl @Inject constructor(private val taskUtils: TaskUtils) : WebApiDataSource{
    override fun retrieveDummyData(): Pair<Boolean, List<Employee>?> {
        if(taskUtils.isInternetAvailable())
            return Pair(true,null)
        return Pair(false,null)
    }
}