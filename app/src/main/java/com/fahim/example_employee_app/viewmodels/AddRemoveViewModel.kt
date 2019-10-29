package com.fahim.example_employee_app.viewmodels

import android.app.Application
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.models.Employee

class AddRemoveViewModel(application: Application) : BaseViewModel(application) {

    init {
        (application as EmployeeApplication).component.inject(this)
    }

    override fun onItemClick(employee: Employee) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}