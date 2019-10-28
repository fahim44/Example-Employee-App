package com.fahim.example_employee_app.repositories

import android.os.AsyncTask
import androidx.paging.toLiveData
import com.fahim.example_employee_app.models.Employee
import com.fahim.example_employee_app.room.EmployeeDao

class EmployeeRepository(private val dao : EmployeeDao) {
    fun getCount() =  dao.getRowCount()

    fun getAllEmployees() = dao.employeesByRating().toLiveData(pageSize = 10)

    fun getSearchedEmployeeList(name:String)/*: LiveData<PagedList<Employee>>*/ = dao.employeesSortByName(name).toLiveData(pageSize = 10)


    fun insertEmployees(employees: List<Employee>) = InsertEmployeeAsyncTask(dao,employees).execute()

    fun updateEmployee(employee: Employee) = UpdateEmployeeAsyncTask(dao,employee).execute()

    fun deleteEmployee(employee: Employee) = DeleteEmployeeAsyncTask(dao,employee).execute()



    companion object {

        private class InsertEmployeeAsyncTask(private val dao: EmployeeDao,private val employees : List<Employee>) : AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                dao.insert(*employees.toTypedArray())
            }
        }

        private class UpdateEmployeeAsyncTask(private val dao: EmployeeDao,private val employee : Employee) : AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                dao.update(employee)
            }
        }

        private class DeleteEmployeeAsyncTask(private val dao: EmployeeDao,private val employee : Employee) : AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                dao.delete(employee)
            }
        }
    }
}