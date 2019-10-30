package com.fahim.example_employee_app.viewmodels

import androidx.lifecycle.ViewModel
import com.fahim.example_employee_app.repositories.EmployeeRepository
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val repository: EmployeeRepository) : ViewModel() {

    var configurationChanged = false

    fun getEmployee(uid:Int) = repository.getEmployee(uid)

    fun updateRating(id:Int, rating: Float) = repository.updateEmployeeRating(id,rating)
}