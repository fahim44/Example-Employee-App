package com.fahim.example_employee_app.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fahim.example_employee_app.repository.EmployeeRepository
import javax.inject.Inject

class ListViewModel @Inject constructor(application: Application,repository: EmployeeRepository) : BaseViewModel(application) {

    val allEmployeeListLD = repository.getAllEmployees()

    private val _navigateToDetailActivityMLD = MutableLiveData<Int>()
    val navigateToDetailActivityLD : LiveData<Int> = _navigateToDetailActivityMLD


    override fun onItemClick(id: Int) {
        _navigateToDetailActivityMLD.value = id
    }

}