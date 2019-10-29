package com.fahim.example_employee_app.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahim.example_employee_app.models.Employee

class ListViewModel(application: Application) : BaseViewModel(application) {
    override fun onItemClick(employee: Employee) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}