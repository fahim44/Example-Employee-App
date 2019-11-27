package com.fahim.example_employee_app.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.repository.EmployeeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RemoveListViewModel @Inject constructor(application: Application,private val repository: EmployeeRepository) : BaseViewModel(application) {

    val allEmployeeListLD = repository.getAllEmployees()

    private val _navigateToAddEditActivityMLD = MutableLiveData<Int>()
    val navigateToAddEditActivityLD : LiveData<Int> = _navigateToAddEditActivityMLD

    private val _notifyAdapterItemChangedMLD = MutableLiveData<Int>()
    val notifyAdapterItemChangedLD : LiveData<Int> = _notifyAdapterItemChangedMLD


    fun delete(employee: Employee?, adapterPosition:Int){
        employee?.let {
            viewModelScope.launch {
                val response = repository.deleteEmployee(it)
                if (response) Toast.makeText(getApplication(),R.string.employee_deleted,Toast.LENGTH_SHORT).show()
                else {
                    _notifyAdapterItemChangedMLD.value = adapterPosition
                    Toast.makeText(getApplication(),R.string.something_wrong,Toast.LENGTH_SHORT).show()
                } } }
    }


    override fun onItemClick(id: Int) {
        _navigateToAddEditActivityMLD.value = id
    }
}