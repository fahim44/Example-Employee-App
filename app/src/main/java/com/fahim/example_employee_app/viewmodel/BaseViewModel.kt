package com.fahim.example_employee_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    abstract fun onItemClick(id: Int)
}