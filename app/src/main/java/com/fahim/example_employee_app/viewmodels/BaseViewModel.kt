package com.fahim.example_employee_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fahim.example_employee_app.models.Employee

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    abstract fun onItemClick(employee: Employee)
}