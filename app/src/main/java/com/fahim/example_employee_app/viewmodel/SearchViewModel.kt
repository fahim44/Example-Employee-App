package com.fahim.example_employee_app.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.repository.EmployeeRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(application: Application,private val repository: EmployeeRepository) : BaseViewModel(application) {

    var searchedEmployeeListLD : LiveData<PagedList<Employee>>? = null

    private val _navigateToDetailActivityMLD = MutableLiveData<Int>()
    val navigateToDetailActivityLD : LiveData<Int> = _navigateToDetailActivityMLD


    fun search(name:String){
        searchedEmployeeListLD = repository.getSearchedEmployeeList(name)
    }

    override fun onItemClick(id: Int) {
        _navigateToDetailActivityMLD.value = id
    }
}