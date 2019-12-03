package com.fahim.example_employee_app.api

import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.util.TaskUtils
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class WebApiDataSourceUnitTests {
    @Mock
    lateinit var taskUtils: TaskUtils

    @Mock
    lateinit var dataService: DummyDataService

    @Mock
    lateinit var call : Call<List<Employee>>

    @Mock
    lateinit var response: Response<List<Employee>>

    @InjectMocks
    lateinit var dataSource : WebApiDataSourceImpl

    @Before
    fun setup(){
        Mockito.`when`(dataService.getDummyEmployeesData()).thenReturn(call)
        Mockito.`when`(call.execute()).thenReturn(response)
    }


    @Test
    fun getRetrieveDummyData_whenInternetNotAvailable_shouldReturnFalseAsResponseSuccessful(){
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(false)
        val result = dataSource.retrieveDummyData()
        Truth.assertThat(result.first).isFalse()
    }


    @Test
    fun getRetrieveDummyData_whenInternetNotAvailable_shouldReturnNullAsResponseList(){
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(false)
        val result = dataSource.retrieveDummyData()
        Truth.assertThat(result.second).isNull()
    }


    @Test
    fun getRetrieveDummyData_whenResponseReturnFalse_shouldReturnFalseAsResponseSuccessful(){
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(response.isSuccessful).thenReturn(false)
        val result = dataSource.retrieveDummyData()
        Truth.assertThat(result.first).isFalse()
    }

    @Test
    fun getRetrieveDummyData_whenResponseReturnFalse_shouldReturnNullAsResponseList(){
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(response.isSuccessful).thenReturn(false)
        val result = dataSource.retrieveDummyData()
        Truth.assertThat(result.second).isNull()
    }

    @Test
    fun getRetrieveDummyData_whenCallExecuteThrowIOException_shouldReturnFalseAsResponseSuccessful(){
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(call.execute()).thenThrow(IOException::class.java)
        val result = dataSource.retrieveDummyData()
        Truth.assertThat(result.first).isFalse()
    }


    @Test
    fun getRetrieveDummyData_whenCallExecuteThrowIOException_shouldReturnNullAsResponseList(){
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(call.execute()).thenThrow(IOException::class.java)
        val result = dataSource.retrieveDummyData()
        Truth.assertThat(result.second).isNull()
    }


    @Test
    fun getRetrieveDummyData_whenCallExecuteThrowRuntimeException_shouldReturnFalseAsResponseSuccessful(){
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(call.execute()).thenThrow(RuntimeException::class.java)
        val result = dataSource.retrieveDummyData()
        Truth.assertThat(result.first).isFalse()
    }


    @Test
    fun getRetrieveDummyData_whenCallExecuteThrowRuntimeException_shouldReturnNullAsResponseList(){
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(call.execute()).thenThrow(RuntimeException::class.java)
        val result = dataSource.retrieveDummyData()
        Truth.assertThat(result.second).isNull()
    }

    @Test
    fun getRetrieveDummyData_whenResponseReturnNullList_shouldReturnFalseAsResponseSuccessful(){
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(null)
        val result = dataSource.retrieveDummyData()
        Truth.assertThat(result.first).isFalse()
    }

    @Test
    fun getRetrieveDummyData_whenResponseReturnNullList_shouldReturnNullAsResponseList(){
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(null)
        val result = dataSource.retrieveDummyData()
        Truth.assertThat(result.second).isNull()
    }


    @Test
    fun getRetrieveDummyData_whenResponseReturnValid_shouldReturnTrueAsResponseSuccessful(){
        val list = listOf(Employee(1,"Fahim",80000f,27,0.0f))
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(list)
        val result = dataSource.retrieveDummyData()
        Truth.assertThat(result.first).isTrue()
    }

    @Test
    fun getRetrieveDummyData_whenResponseReturnValid_shouldReturnValidListAsResponseList(){
        val list = listOf(Employee(1,"Fahim",80000f,27,0.0f))
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(list)
        val result = dataSource.retrieveDummyData()
        Truth.assertThat(result.second).isEqualTo(list)
    }
}
