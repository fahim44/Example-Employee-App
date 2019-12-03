package com.fahim.example_employee_app.api

import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.util.TaskUtils
import com.orhanobut.logger.Logger
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WebApiDataSourceImpl @Inject constructor(private val dataService: DummyDataService,private val taskUtils: TaskUtils) : WebApiDataSource{
    override fun retrieveDummyData(): Pair<Boolean, List<Employee>?> {
        if(taskUtils.isInternetAvailable()){
            val call = dataService.getDummyEmployeesData()
            try {
                val response = call.execute()
                Logger.d(response)
                if(response.isSuccessful){
                    val list = response.body()
                    list?.let { return Pair(true,list) }
                    return Pair(false,null)
                }
                return Pair(false,null)
            }catch (e:IOException){
                return Pair(false,null)
            }catch (e:RuntimeException){
                return Pair(false,null)
            }
        }
        return Pair(false,null)
    }
}