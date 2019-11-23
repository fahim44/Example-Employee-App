package com.fahim.example_employee_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val _showToastMLD = MutableLiveData<Int>()
    val showToastLD : LiveData<Int> = _showToastMLD

    abstract fun onItemClick(id: Int)
}