package com.fahim.example_employee_app.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.models.Employee
import com.fahim.example_employee_app.repositories.EmployeeRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: EmployeeRepository) : BaseViewModel() {

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