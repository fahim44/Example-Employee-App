package com.fahim.example_employee_app.viewmodel

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    abstract fun onItemClick(id: Int)
}