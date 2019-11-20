package com.fahim.example_employee_app.repository

import androidx.paging.Config
import androidx.paging.toLiveData
import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.api.DummyDataService
import com.fahim.example_employee_app.db.EmployeeDao
import com.fahim.example_employee_app.preference.SharedPreference
import com.fahim.example_employee_app.util.TaskUtils
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeRepositoryImpl @Inject constructor(private val dao : EmployeeDao,
                                                 private val dataService: DummyDataService,
                                                 private val preference: SharedPreference,
                                                 private val taskUtils: TaskUtils)
    : EmployeeRepository{

    override fun isDummyDataLoaded() = preference.isInitDataLoaded()

    override fun setDummyDataLoaded() {
        preference.initDataLoaded(true)
    }

    override fun getEmployee(uid:Int) = dao.getEmployee(uid)

    override fun getAllEmployees()  = dao.getAllEmployees().toLiveData(Config(pageSize = 30,enablePlaceholders = true,maxSize = 1000))

    override fun getSearchedEmployeeList(name:String) = dao.employeesSortByName(name).toLiveData(Config(pageSize = 30,enablePlaceholders = true,maxSize = 1000))


    override suspend fun insertEmployees(employees: List<Employee>) : Boolean {
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

    override suspend fun updateEmployeeRating(id: Int, rating : Float){
        withContext(Dispatchers.IO){
            dao.updateRating(id,rating)
        }
    }

    override suspend fun updateEmployee(employee: Employee) : Boolean {
        var result = 0
        withContext(Dispatchers.IO){
            result = dao.update(employee)
        }
        return (result!= 0)
    }

    override suspend fun deleteEmployee(employee: Employee) {
        withContext(Dispatchers.IO) {
            dao.delete(employee)
        }
    }


    override suspend fun getDummyDataFromServerAndLoadToLocalDB() : Boolean {
        var result = false
        if (taskUtils.isInternetAvailable()) {
            val serverData = retrieveDataFromServer()
            if(serverData.first)
                result = insertEmployees(serverData.second)
        }
        return result
    }

    override suspend fun retrieveDataFromServer(): Pair<Boolean,List<Employee>> {
        var list : List<Employee> = arrayListOf()
        var validResponse = false
        withContext(Dispatchers.IO){
            val call = dataService.getDummyEmployeesData()
            val response = call.execute()
            Logger.d(response)
            if(response.isSuccessful) {
                list = response.body()!!
                validResponse = true
            }
        }
        return Pair(validResponse,list)
    }
}