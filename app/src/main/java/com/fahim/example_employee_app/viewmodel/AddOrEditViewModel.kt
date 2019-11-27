package com.fahim.example_employee_app.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.repository.EmployeeRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddOrEditViewModel @Inject constructor(application:Application, private val repository: EmployeeRepository) : AndroidViewModel(application) {


    // if uid < 0, then add, if uid>=0, then edit employee
    var uid = -1
    var rating = 0.0f

    private val _onBackPressMLD = MutableLiveData<Boolean>()
    val onBackPressLD : LiveData<Boolean> = _onBackPressMLD

    fun getEmployee(uid:Int) = repository.getEmployee(uid)


    fun doneButtonPressed(name:String, id:String, age:String, salary:String){
        if(validateInput(name,id,age,salary))
            addOrEditEmployee(uid,name,id,age,salary,rating)
        else
            Toast.makeText(getApplication(),R.string.invalid_info,Toast.LENGTH_SHORT).show()
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
                Toast.makeText(getApplication(),R.string.success,Toast.LENGTH_SHORT).show()
                _onBackPressMLD.value = true
            }
            else
                Toast.makeText(getApplication(),R.string.something_wrong,Toast.LENGTH_SHORT).show()
        }
    }

    private fun editEmployee(uid:Int, name:String, id:Int, age:Int, salary:Float, rating:Float){
        val emp = Employee(id,name,salary,age,rating)
        emp.uid = uid
        viewModelScope.launch {
            val result = repository.updateEmployee(emp)
            if(result){
                Toast.makeText(getApplication(),R.string.success,Toast.LENGTH_SHORT).show()
                _onBackPressMLD.value = true
            }
            else {
                Toast.makeText(getApplication(),R.string.something_wrong,Toast.LENGTH_SHORT).show()
            }
        }

    }
}