package com.fahim.example_employee_app.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.utils.SharedPreference
import javax.inject.Inject

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var preference : SharedPreference

    init {
        (application as EmployeeApplication).component.inject(this)
    }
}