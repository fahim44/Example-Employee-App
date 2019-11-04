package com.fahim.example_employee_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.repository.EmployeeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddOrEditViewModel @Inject constructor(private val repository: EmployeeRepository) : ViewModel() {


    // if uid < 0, then add, if uid>=0, then edit employee
    var uid = -1
    var rating = 0.0f

    private val _toastMLD = MutableLiveData<Int>()
    val toastLD : LiveData<Int> = _toastMLD

    private val _onBackPressMLD = MutableLiveData<Boolean>()
    val onBackPressLD : LiveData<Boolean> = _onBackPressMLD

    fun getEmployee(uid:Int) = repository.getEmployee(uid)


    fun doneButtonPressed(name:String, id:String, age:String, salary:String){
        if(validateInput(name,id,age,salary)){
            addOrEditEmployee(uid,name,id,age,salary,rating)
        }else {
            _toastMLD.value = R.string.invalid_info
        }
    }

    private fun validateInput(name:String, id:String, age:String, salary:String):Boolean{
        if(name.trim()=="" || id.trim()=="" || age.trim()=="" || salary.trim()=="")
            return false
        return true
    }


    private fun addOrEditEmployee(uid:Int, name:String, id:String, age:String, salary:String, rating:Float){
        val mId = id.toInt()
        val mAge = age.toInt()
        val mSalary = salary.toFloat()
        if(uid<0)
            addEmployee(name,mId,mAge,mSalary)
        else
            editEmployee(uid,name,mId,mAge,mSalary,rating)

    }

    private fun addEmployee(name:String, id:Int, age:Int, salary:Float){
        viewModelScope.launch {
           val result = repository.insertEmployees(listOf(Employee(id,name,salary,age,0.0f)))
            if(result){
                _toastMLD.value = R.string.success
                _onBackPressMLD.value = true
            }
            else {
                _toastMLD.value = R.string.something_wrong
            }
        }
    }

    private fun editEmployee(uid:Int, name:String, id:Int, age:Int, salary:Float, rating:Float){
        val emp = Employee(id,name,salary,age,rating)
        emp.uid = uid
        viewModelScope.launch {
            val result = repository.updateEmployee(emp)
            if(result){
                _toastMLD.value = R.string.success
                _onBackPressMLD.value = true
            }
            else {
                _toastMLD.value = R.string.something_wrong
            }
        }

    }
}