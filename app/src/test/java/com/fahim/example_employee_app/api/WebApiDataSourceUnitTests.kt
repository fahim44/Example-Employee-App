package com.fahim.example_employee_app.api

import com.fahim.example_employee_app.util.TaskUtils
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WebApiDataSourceUnitTests {
    @Mock
    lateinit var taskUtils: TaskUtils

    @Mock
    lateinit var dataService: DummyDataService

    @InjectMocks
    lateinit var dataSource : WebApiDataSourceImpl


    @Test
    fun getRetrieveDummyData_whenInternetNotAvailable_shouldReturnFalseAndNullList(){
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(false)
        val response = dataSource.retrieveDummyData()
        Truth.assertThat(response).isEqualTo(Pair(false,null))
    }


    @Test
    fun getRetrieveDummyData_whenDataRetrieveSuccessful_shouldReturnTrueAndList(){
    }
}
