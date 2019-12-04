package com.fahim.example_employee_app.repository

import androidx.paging.Config
import androidx.paging.toLiveData
import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.api.WebApiDataSource
import com.fahim.example_employee_app.db.LocalDBDataSource
import com.fahim.example_employee_app.preference.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeRepositoryImpl @Inject constructor(private val dbDataSource: LocalDBDataSource,
                                                 private val apiDataSource: WebApiDataSource,
                                                 private val preference: SharedPreference)
    : EmployeeRepository{

    override fun isDummyDataLoaded() = preference.isInitDataLoaded()

    override fun setDummyDataLoaded() = preference.initDataLoaded(true)

    override fun getEmployee(uid:Int) = dbDataSource.getEmployee(uid)

    override fun getAllEmployees() = dbDataSource.getAllEmployees().toLiveData(Config(pageSize = 30,enablePlaceholders = true,maxSize = 1000))

    override fun getSearchedEmployeeList(name:String) = dbDataSource.employeesSortByName(name).toLiveData(Config(pageSize = 30,enablePlaceholders = true,maxSize = 1000))


    override suspend fun insertEmployees(employees: List<Employee>) : Boolean {
        var list:List<Long>? = null
        withContext(Dispatchers.IO){
            list = dbDataSource.insertEmployees(employees)
        }
        if(list==null)
            return false
        else if(list!!.isEmpty())
            return false
        return true
    }

    override suspend fun updateEmployeeRating(id: Int, rating : Float) : Boolean{
        if(id>=0 && rating>=0 && rating <= 5.0f) {
            var result = false
            withContext(Dispatchers.IO) {
                result = dbDataSource.updateRating(id, rating)
            }
            return result
        }
        return false
    }

    override suspend fun updateEmployee(employee: Employee) = withContext(Dispatchers.IO){
        return@withContext dbDataSource.updateEmployee(employee)
    }

    override suspend fun deleteEmployee(employee: Employee) = withContext(Dispatchers.IO){
        return@withContext dbDataSource.deleteEmployee(employee)
    }

    override suspend fun getDummyDataFromServerAndLoadToLocalDB() : Boolean {
        val serverData = retrieveDataFromServer()
        if(serverData.first)
            return insertEmployees(serverData.second!!)
        return false
    }


    override suspend fun retrieveDataFromServer() = withContext(Dispatchers.IO){
        return@withContext apiDataSource.retrieveDummyData()
    }
}