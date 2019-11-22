package com.fahim.example_employee_app


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.*
import androidx.work.testing.TestListenableWorkerBuilder
import com.fahim.example_employee_app.repository.EmployeeRepositoryImpl
import com.fahim.example_employee_app.workManager.DummyDataRetrieveWorker
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations



@RunWith(AndroidJUnit4::class)
class DummyDataRetrieveWorkerTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    @Mock
    lateinit var employeeRepositoryImpl: EmployeeRepositoryImpl

    private lateinit var context: Context

    inner class TestWorkerFactory : WorkerFactory(){
        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
        ): ListenableWorker? {
            return DummyDataRetrieveWorker(appContext,workerParameters,employeeRepositoryImpl)
        }
    }



    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun dataAlreadyLoaded(){
        Mockito.`when`(employeeRepositoryImpl.isDummyDataLoaded()).thenReturn(true)
        val workerBuilder = TestListenableWorkerBuilder<DummyDataRetrieveWorker>(context)
        workerBuilder.setWorkerFactory(TestWorkerFactory())
        val worker = workerBuilder.build()
        runBlocking {
            val result = worker.doWork()
            assertThat(result, `is`(ListenableWorker.Result.success()))
            Mockito.verify(employeeRepositoryImpl).isDummyDataLoaded()
        }
    }


    @Test
    fun retryWhenServerDataFetchedFailed(){
        Mockito.`when`(employeeRepositoryImpl.isDummyDataLoaded()).thenReturn(false)
        val workerBuilder = TestListenableWorkerBuilder<DummyDataRetrieveWorker>(context)
        workerBuilder.setWorkerFactory(TestWorkerFactory())
        val worker = workerBuilder.build()
        runBlocking {
            Mockito.`when`(employeeRepositoryImpl.retrieveDataFromServer()).thenReturn(Pair(false,ArrayList()))
            val result = worker.doWork()
            assertThat(result, `is`(ListenableWorker.Result.retry()))
            Mockito.verify(employeeRepositoryImpl).isDummyDataLoaded()
            Mockito.verify(employeeRepositoryImpl).retrieveDataFromServer()
        }
    }

    @Test
    fun insertDataAndUpdatePref(){
        Mockito.`when`(employeeRepositoryImpl.isDummyDataLoaded()).thenReturn(false)
        val workerBuilder = TestListenableWorkerBuilder<DummyDataRetrieveWorker>(context)
        workerBuilder.setWorkerFactory(TestWorkerFactory())
        val worker = workerBuilder.build()
        runBlocking {
            Mockito.`when`(employeeRepositoryImpl.retrieveDataFromServer()).thenReturn(Pair(true,ArrayList()))
            Mockito.`when`(employeeRepositoryImpl.insertEmployees(com.nhaarman.mockitokotlin2.any())).thenReturn(true)
            val result = worker.doWork()
            assertThat(result, `is`(ListenableWorker.Result.success()))
            Mockito.verify(employeeRepositoryImpl).isDummyDataLoaded()
            Mockito.verify(employeeRepositoryImpl).retrieveDataFromServer()
            Mockito.verify(employeeRepositoryImpl).insertEmployees(com.nhaarman.mockitokotlin2.any())
            Mockito.verify(employeeRepositoryImpl).setDummyDataLoaded()
        }
    }


}