/*
package com.fahim.example_employee_app

import com.fahim.example_employee_app.api.DummyDataService
import com.fahim.example_employee_app.db.LocalDBDataSource
import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.preference.SharedPreference
import com.fahim.example_employee_app.repository.EmployeeRepositoryImpl
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
    lateinit var dbDataSource: LocalDBDataSource

    @Mock
    lateinit var dataService: DummyDataService

    @Mock
    lateinit var preference: SharedPreference

    @Mock
    lateinit var taskUtils: TaskUtils

    @Mock
    lateinit var call : Call<List<Employee>>

    @Mock
    lateinit var response: Response<List<Employee>>

    @InjectMocks
    lateinit var repository : EmployeeRepositoryImpl


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
        Mockito.`when`(dbDataSource.insertEmployees(employee)).thenReturn(listOf(1L))
        runBlocking {
            launch(Dispatchers.Main){
                assert(repository.insertEmployees(employee))
                Mockito.verify(dbDataSource).insertEmployees(employee)
            } }
    }


    @Test
    fun insertEmployee__DaoReturnEmptyList(){
        val employee = listOf(Employee(1,"name",1000f,21,4.1f))
        Mockito.`when`(dbDataSource.insertEmployees(employee)).thenReturn(ArrayList())
        runBlocking {
            launch(Dispatchers.Main){
                assert(!repository.insertEmployees(employee))
                Mockito.verify(dbDataSource).insertEmployees(employee)
            } }
    }

    @Test
    fun insertEmployeeD__aoReturnNull(){
        val employee = listOf(Employee(1,"name",1000f,21,4.1f))
        Mockito.`when`(dao.insert(*employee.toTypedArray())).thenReturn(null)
        runBlocking {
            launch(Dispatchers.Main){
                assert(!repository.insertEmployees(employee))
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
            } }
    }


    @Test
    fun updateEmployee__DaoReturn0(){
        val employee = Employee(1,"name",1000f,21,4.1f)
        Mockito.`when`(dao.update(employee)).thenReturn(0)
        runBlocking {
            launch(Dispatchers.Main){
                assert(!repository.updateEmployee(employee))
                Mockito.verify(dao).update(employee)
            } }
    }

    @Test
    fun updateEmployee__DaoReturnNegativeNumber(){
        val employee = Employee(1,"name",1000f,21,4.1f)
        Mockito.`when`(dao.update(employee)).thenReturn(-1)
        runBlocking {
            launch(Dispatchers.Main){
                assert(!repository.updateEmployee(employee))
                Mockito.verify(dao).update(employee)
            } }
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
    fun retrieveDataFromServer__IsFailed(){
        Mockito.`when`(response.isSuccessful).thenReturn(false)
        Mockito.`when`(response.body()).thenReturn(null)
        Mockito.`when`(call.execute()).thenReturn(response)
        Mockito.`when`(dataService.getDummyEmployeesData()).thenReturn(call)

        runBlocking {
            launch(Dispatchers.Main){
                val result = repository.retrieveDataFromServer()
                assert(!result.first)
                assert(result.second.isEmpty())

                Mockito.verify(dataService).getDummyEmployeesData()
                Mockito.verify(call).execute()
                Mockito.verify(response).isSuccessful
            } }
    }


    @Test
    fun retrieveDataFromServer__ResponseTrueButGsonConversionFailed(){
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(null)
        Mockito.`when`(call.execute()).thenReturn(response)
        Mockito.`when`(dataService.getDummyEmployeesData()).thenReturn(call)

        runBlocking {
            launch(Dispatchers.Main){
                val result = repository.retrieveDataFromServer()
                assert(!result.first)
                assert(result.second.isEmpty())

                Mockito.verify(dataService).getDummyEmployeesData()
                Mockito.verify(call).execute()
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


    @Test
    fun getDummyDataFromServerAndLoadToLocalDB__NetworkNotAvailable(){
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(false)
        runBlocking {
            launch(Dispatchers.Main){
                val result = repository.getDummyDataFromServerAndLoadToLocalDB()
                assert(!result)

                Mockito.verify(taskUtils).isInternetAvailable()
            } }
    }

    @Test
    fun getDummyDataFromServerAndLoadToLocalDB__ServerRetrialFailed(){
        Mockito.`when`(response.isSuccessful).thenReturn(false)
        Mockito.`when`(response.body()).thenReturn(null)
        Mockito.`when`(call.execute()).thenReturn(response)
        Mockito.`when`(dataService.getDummyEmployeesData()).thenReturn(call)
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(true)

        runBlocking {
            launch(Dispatchers.Main){
                val result = repository.getDummyDataFromServerAndLoadToLocalDB()
                assert(!result)

                //Mockito.verify(dao).insert(*employee.toTypedArray())
                Mockito.verify(taskUtils).isInternetAvailable()
                Mockito.verify(dataService).getDummyEmployeesData()
                Mockito.verify(call).execute()
                Mockito.verify(response).isSuccessful
            } }
    }

    @Test
    fun getDummyDataFromServerAndLoadToLocalDB__InsertIntoDBFailed(){
        val employee = listOf(Employee(1,"name",1000f,21,4.1f))
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(employee)
        Mockito.`when`(call.execute()).thenReturn(response)
        Mockito.`when`(dataService.getDummyEmployeesData()).thenReturn(call)
        Mockito.`when`(taskUtils.isInternetAvailable()).thenReturn(true)
        Mockito.`when`(dao.insert(*employee.toTypedArray())).thenReturn(null)

        runBlocking {
            launch(Dispatchers.Main){
                val result = repository.getDummyDataFromServerAndLoadToLocalDB()
                assert(!result)

                Mockito.verify(dao).insert(*employee.toTypedArray())
                Mockito.verify(taskUtils).isInternetAvailable()
                Mockito.verify(dataService).getDummyEmployeesData()
                Mockito.verify(call).execute()
                Mockito.verify(response).body()
                Mockito.verify(response).isSuccessful
            } }
    }

    @Test
    fun updateEmployeeRating(){
        val id = 1
        val rating = 2.3f
        runBlocking {
            launch(Dispatchers.Main){
                assert(repository.updateEmployeeRating(id,rating))
                Mockito.verify(dao).updateRating(id,rating)
            } }
    }

    @Test
    fun updateEmployeeRating_invalidId(){
        val id = -10
        val rating = 2.3f
        runBlocking {
            launch(Dispatchers.Main){
                assert(!repository.updateEmployeeRating(id,rating))
            } }
    }

    @Test
    fun updateEmployeeRating_outOfScopeRating(){
        val id = 1
        val rating = 5.001f
        runBlocking {
            launch(Dispatchers.Main){
                assert(!repository.updateEmployeeRating(id,rating))
            } }
    }

    @Test
    fun deleteEmployee(){
        val employee = Employee(1,"name",1000f,21,4.1f)
        Mockito.`when`(dao.delete(employee)).thenReturn(1)
        runBlocking {
            launch(Dispatchers.Main){
                assert(repository.deleteEmployee(employee))
                Mockito.verify(dao).delete(employee)
            } }
    }

    @Test
    fun deleteEmployee_DaoReturn0(){
        val employee = Employee(1,"name",1000f,21,4.1f)
        Mockito.`when`(dao.delete(employee)).thenReturn(0)
        runBlocking {
            launch(Dispatchers.Main){
                assert(!repository.deleteEmployee(employee))
                Mockito.verify(dao).delete(employee)
            } }
    }

    @Test
    fun deleteEmployee_DaoReturnNegativeNumber(){
        val employee = Employee(1,"name",1000f,21,4.1f)
        Mockito.`when`(dao.delete(employee)).thenReturn(-41)
        runBlocking {
            launch(Dispatchers.Main){
                assert(!repository.deleteEmployee(employee))
                Mockito.verify(dao).delete(employee)
            } }
    }
}
*/
