package com.fahim.example_employee_app.repository

import androidx.paging.Config
import androidx.paging.toLiveData
import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.api.DummyDataService
import com.fahim.example_employee_app.db.EmployeeDao
import com.fahim.example_employee_app.preference.SharedPreference
import com.fahim.example_employee_app.util.AppExecutors
import com.fahim.example_employee_app.util.TaskUtils
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeRepository @Inject constructor(private val dao : EmployeeDao, private val dataService: DummyDataService, private val preference: SharedPreference, private val taskUtils: TaskUtils, private val executor : AppExecutors) {

    fun isDummyDataLoaded() = preference.isInitDataLoaded()

    fun setDummyDataLoaded() {
        preference.initDataLoaded(true)
    }

    fun getEmployee(uid:Int) = dao.getEmployee(uid)

    fun getAllEmployees()  = dao.getAllEmployees().toLiveData(Config(pageSize = 30,enablePlaceholders = true,maxSize = 1000))

    fun getSearchedEmployeeList(name:String) = dao.employeesSortByName(name).toLiveData(Config(pageSize = 30,enablePlaceholders = true,maxSize = 1000))


    suspend fun insertEmployees(employees: List<Employee>) : Boolean {
        var list:List<Long>? = null
        withContext(Dispatchers.IO){
            list = dao.insert(*employees.toTypedArray())
        }
        if(list==null)
            return false
        else if(list!!.isEmpty())
            return false
        return true
    }

    fun updateEmployeeRating(id: Int, rating : Float) =  executor.diskIOExecute { dao.updateRating(id,rating) }

    suspend fun updateEmployee(employee: Employee) : Boolean {
        var result = 0
        withContext(Dispatchers.IO){
            result = dao.update(employee)
        }
        return (result!= 0)
    }

    fun deleteEmployee(employee: Employee) = executor.diskIOExecute { dao.delete(employee) }


    suspend fun getDummyDataFromServiceAndLoadToLocalDB() : Boolean {
        var result = false
        if (taskUtils.isInternetAvailable()) {
            withContext(Dispatchers.IO){
                val call = dataService.getDummyEmployeesData()
                val response = call.execute()
                Logger.d(response)
                result = insertEmployees(response.body()!!)
            }
        }
        return result
    }
}