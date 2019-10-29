package com.fahim.example_employee_app.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.models.Employee
import com.fahim.example_employee_app.repositories.EmployeeRepository
import javax.inject.Inject

class RemoveListViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var repository: EmployeeRepository

    val allEmployeeListLD : LiveData<PagedList<Employee>>

    private val _navigateToAddEditActivityMLD = MutableLiveData<Int>()
    val navigateToAddEditActivityLD : LiveData<Int> = _navigateToAddEditActivityMLD

    init {
        (application as EmployeeApplication).component.inject(this)

        allEmployeeListLD = repository.getAllEmployees()
    }


    fun delete(employee: Employee?){
        employee?.let {
            repository.deleteEmployee(it)
        }
    }

    override fun onItemClick(id: Int) {
        _navigateToAddEditActivityMLD.value = id
    }
}