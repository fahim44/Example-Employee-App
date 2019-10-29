package com.fahim.example_employee_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.models.Employee
import com.fahim.example_employee_app.repositories.EmployeeRepository
import javax.inject.Inject

class AddOrEditViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var repository: EmployeeRepository

    init {
        (application as EmployeeApplication).component.inject(this)
    }

    fun getEmployee(uid:Int) = repository.getEmployee(uid)


    fun validateInput(name:String, id:String, age:String, salary:String):Boolean{
        if(name.trim()=="" || id.trim()=="" || age.trim()=="" || salary.trim()=="")
            return false
        return true
    }


    fun addOrEditEmployee(uid:Int, name:String, id:String, age:String, salary:String, rating:Float){
        val _id = id.toInt()
        val _age = age.toInt()
        val _salary = salary.toFloat()
        if(uid<0)
            addEmployee(name,_id,_age,_salary)
        else
            editEmployee(uid,name,_id,_age,_salary,rating)

    }

    private fun addEmployee(name:String, id:Int, age:Int, salary:Float){
        repository.insertEmployees(listOf(Employee(id,name,salary,age,0.0f)))
    }

    private fun editEmployee(uid:Int, name:String, id:Int, age:Int, salary:Float, rating:Float){
        val emp = Employee(id,name,salary,age,rating)
        emp.uid = uid
        repository.updateEmployee(emp)
    }
}