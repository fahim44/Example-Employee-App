package com.fahim.example_employee_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahim.example_employee_app.repository.EmployeeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repository: EmployeeRepository) : ViewModel() {

    var uid = -1

    fun getEmployee() = repository.getEmployee(uid)

    fun updateRating(rating: Float) {
        if (uid>=0)
            viewModelScope.launch {
                repository.updateEmployeeRating(uid,rating)
            }
    }
}