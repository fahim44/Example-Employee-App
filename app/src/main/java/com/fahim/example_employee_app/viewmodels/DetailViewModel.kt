package com.fahim.example_employee_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.repositories.EmployeeRepository
import javax.inject.Inject

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repository: EmployeeRepository

    init {
        (application as EmployeeApplication).component.inject(this)
    }

    fun getEmployee(uid:Int) = repository.getEmployee(uid)

    fun updateRating(id:Int, rating: Float) = repository.updateEmployeeRating(id,rating)
}