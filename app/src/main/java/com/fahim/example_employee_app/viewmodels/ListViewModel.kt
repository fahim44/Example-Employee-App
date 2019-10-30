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

class ListViewModel @Inject constructor(repository: EmployeeRepository) : BaseViewModel() {

    val allEmployeeListLD = repository.getAllEmployees()

    private val _navigateToDetailActivityMLD = MutableLiveData<Int>()
    val navigateToDetailActivityLD : LiveData<Int> = _navigateToDetailActivityMLD


    override fun onItemClick(id: Int) {
        _navigateToDetailActivityMLD.value = id
    }

}