package com.fahim.example_employee_app.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.models.Employee
import com.fahim.example_employee_app.repositories.EmployeeRepository
import javax.inject.Inject

class ListViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var repository: EmployeeRepository

    val allEmployeeListLD : LiveData<PagedList<Employee>>

    init {
        (application as EmployeeApplication).component.inject(this)

        allEmployeeListLD = repository.getAllEmployees()
    }

    override fun onItemClick(employee: Employee) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}