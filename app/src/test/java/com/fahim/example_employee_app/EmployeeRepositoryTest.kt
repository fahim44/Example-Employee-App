package com.fahim.example_employee_app

import com.fahim.example_employee_app.api.DummyDataService
import com.fahim.example_employee_app.db.EmployeeDao
import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.preference.SharedPreference
import com.fahim.example_employee_app.repository.EmployeeRepository
import com.fahim.example_employee_app.util.AppExecutors
import com.fahim.example_employee_app.util.TaskUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Response


@Suppress("Unused")
@UseExperimental(ObsoleteCoroutinesApi::class,ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class EmployeeRepositoryTest {
    @Mock
    lateinit var dao: EmployeeDao

    @Mock
    lateinit var dataService: DummyDataService

    @Mock
    lateinit var preference: SharedPreference

    @Mock
    lateinit var taskUtils: TaskUtils

    @Mock
    lateinit var executor : AppExecutors

    @Mock
    lateinit var call : Call<List<Employee>>

    @Mock
    lateinit var response: Response<List<Employee>>

    @InjectMocks
    lateinit var repository : EmployeeRepository


    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Test
    fun insertEmployees(){
        val employee = listOf(Employee(1,"name",1000f,21,4.1f))
        Mockito.`when`(dao.insert(*employee.toTypedArray())).thenReturn(listOf(1L))
        runBlocking {
            launch(Dispatchers.Main){
                assert(repository.insertEmployees(employee))
                Mockito.verify(dao).insert(*employee.toTypedArray())
            } }
    }

    @Test
    fun updateEmployee(){
        val employee = Employee(1,"name",1000f,21,4.1f)
        Mockito.`when`(dao.update(employee)).thenReturn(1)
        runBlocking {
            launch(Dispatchers.Main){
                assert(repository.updateEmployee(employee))
                Mockito.verify(dao).update(employee)
            }
        }
    }


    @Test
    fun retrieveDataFromServer(){
        val employee = listOf(Employee(1,"name",1000f,21,4.1f))
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(employee)
        Mockito.`when`(call.execute()).thenReturn(response)
        Mockito.`when`(dataService.getDummyEmployeesData()).thenReturn(call)

        runBlocking {
            launch(Dispatchers.Main){
                val result = repository.retrieveDataFromServer()
                assert(result.first)
                Assert.assertEquals(employee,result.second)

                Mockito.verify(dataService).getDummyEmployeesData()
                Mockito.verify(call).execute()
                Mockito.verify(response).body()
                Mockito.verify(response).isSuccessful
            } }
    }


    @Test
    fun getDummyDataFromServerAndLoadToLocalDB(){
        val employee = listOf(Employee(1,"name",1000f,21,4.1f))
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(employee)
        Mockito.`when`(call.execute()).thenReturn(response)
        Mockito.`when`(dataService.getDummyEmployeesData()).thenReturn(call)
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(dao.insert(*employee.toTypedArray())).thenReturn(listOf(1L))

        runBlocking {
            launch(Dispatchers.Main){
                val result = repository.getDummyDataFromServerAndLoadToLocalDB()
                assert(result)

                Mockito.verify(dao).insert(*employee.toTypedArray())
                Mockito.verify(taskUtils).isInternetAvailable()
                Mockito.verify(dataService).getDummyEmployeesData()
                Mockito.verify(call).execute()
                Mockito.verify(response).body()
                Mockito.verify(response).isSuccessful
            } }
    }
}