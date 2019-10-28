package com.fahim.example_employee_app.repositories

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.fahim.example_employee_app.models.Employee
import com.fahim.example_employee_app.retrofit.DummyDataService
import com.fahim.example_employee_app.room.EmployeeDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeRepository(private val dao : EmployeeDao, private val dataService: DummyDataService) {
    private val dummyDataLoaded = MutableLiveData<Boolean>()


    //fun getCount() =  dao.getRowCount()

    fun getAllEmployees() = dao.employeesByRating().toLiveData(pageSize = 10)

    fun getSearchedEmployeeList(name:String)/*: LiveData<PagedList<Employee>>*/ = dao.employeesSortByName(name).toLiveData(pageSize = 10)


    fun insertEmployees(employees: List<Employee>) = InsertEmployeeAsyncTask(dao,employees,dummyDataLoaded).execute()

    fun updateEmployee(employee: Employee) = UpdateEmployeeAsyncTask(dao,employee).execute()

    fun deleteEmployee(employee: Employee) = DeleteEmployeeAsyncTask(dao,employee).execute()


    fun getDummyDataFromServiceAndLoadToLocalDB() : LiveData<Boolean> {
        val call = dataService.getDummyEmployeesData()
        call.enqueue(object : Callback<List<Employee>> {
            override fun onResponse(call: Call<List<Employee>>, response: Response<List<Employee>>) {
                insertEmployees(response.body()!!)
            }

            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {
                dummyDataLoaded.value = false
            }
        })
        return dummyDataLoaded
    }

    companion object {

        private class InsertEmployeeAsyncTask(private val dao: EmployeeDao,private val employees : List<Employee>, private val liveData : MutableLiveData<Boolean>) : AsyncTask<Unit,Unit,Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                dao.insert(*employees.toTypedArray())
            }

            override fun onPostExecute(result: Unit?) {
                super.onPostExecute(result)
                liveData.value = true
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