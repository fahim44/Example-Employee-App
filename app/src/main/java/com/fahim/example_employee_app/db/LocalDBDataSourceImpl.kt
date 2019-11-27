package com.fahim.example_employee_app.db

import androidx.paging.DataSource
import com.fahim.example_employee_app.model.Employee
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDBDataSourceImpl @Inject constructor(private val dao:EmployeeDao) : LocalDBDataSource {
    override fun insertEmployees(employees: List<Employee?>?) : List<Long>? {
        val values = arrayListOf<Employee>()
        employees?.forEach { it?.let { values.add(it) } }
        if(values.size>0)
            return dao.insert(*values.toTypedArray())
        return null
    }


    override fun getAllEmployees() = dao.getAllEmployees()


    override fun employeesSortByName(name: String?): DataSource.Factory<Int, Employee> {
        if (name==null)
            return dao.getAllEmployees()
        return dao.employeesSortByName(name)
    }


    override fun getEmployee(uid: Int) = dao.getEmployee(uid)

    override fun updateEmployee(employee: Employee?): Boolean {
        employee?.let { return dao.update(it)>0 }
        return false
    }

    override fun updateRating(id: Int, rating: Float): Boolean {
        return dao.updateRating(id,rating)>0
    }

    override fun deleteEmployee(employee: Employee?): Boolean {
        employee?.let { return dao.delete(it)>0 }
        return false
    }
}