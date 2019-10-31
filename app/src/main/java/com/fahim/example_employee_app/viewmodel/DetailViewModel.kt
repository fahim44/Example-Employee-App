package com.fahim.example_employee_app.viewmodel

import androidx.lifecycle.ViewModel
import com.fahim.example_employee_app.repository.EmployeeRepository
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repository: EmployeeRepository) : ViewModel() {

    fun getEmployee(uid:Int) = repository.getEmployee(uid)

    fun updateRating(id:Int, rating: Float) = repository.updateEmployeeRating(id,rating)
}