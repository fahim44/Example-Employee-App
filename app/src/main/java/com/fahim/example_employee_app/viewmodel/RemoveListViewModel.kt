package com.fahim.example_employee_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.repository.EmployeeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RemoveListViewModel @Inject constructor(private val repository: EmployeeRepository) : BaseViewModel() {

    val allEmployeeListLD = repository.getAllEmployees()

    private val _navigateToAddEditActivityMLD = MutableLiveData<Int>()
    val navigateToAddEditActivityLD : LiveData<Int> = _navigateToAddEditActivityMLD


    fun delete(employee: Employee?){
        employee?.let {
            viewModelScope.launch {
                repository.deleteEmployee(it)
            } }
    }


    override fun onItemClick(id: Int) {
        _navigateToAddEditActivityMLD.value = id
    }
}