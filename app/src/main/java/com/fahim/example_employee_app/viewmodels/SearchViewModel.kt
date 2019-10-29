package com.fahim.example_employee_app.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.models.Employee

class SearchViewModel(application: Application) : BaseViewModel(application) {

    init {
        (application as EmployeeApplication).component.inject(this)
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    override fun onItemClick(employee: Employee) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}