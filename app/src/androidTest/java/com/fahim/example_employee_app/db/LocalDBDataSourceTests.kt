package com.fahim.example_employee_app.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.Config
import androidx.paging.toLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fahim.example_employee_app.TestUtils
import com.fahim.example_employee_app.model.Employee
import com.google.common.truth.Truth
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalDBDataSourceTests {
    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private lateinit var dataSource : LocalDBDataSource
    private lateinit var db : EmployeeDatabase

    private val name = "Fahim"
    private val employee = Employee(id = 1,name = name,salary = 2000.0f,age = 21,rating = 5.0f)


    @Before
    fun setUp(){
        val context : Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context,EmployeeDatabase::class.java)
            .build()
        val dao = db.employeeDao()
        dataSource = LocalDBDataSourceImpl(dao)
    }


    @After
    @Throws(IOException::class)
    fun tearDown(){
        db.close()
    }


    @Test
    fun getInsertEmployee_whenInsertingValidEmployee_shouldInsertIntoDB(){
        val employees = listOf(employee)
        Truth.assertThat(dataSource.insertEmployees(employees)).hasSize(1)
    }

    @Test
    fun getInsertEmployee_whenInsertingNullEmployeeList_shouldNotInsertIntoDB(){
        Truth.assertThat(dataSource.insertEmployees(null)).isNull()
    }

    @Test
    fun getInsertEmployee_whenInsertNullEmployee_shouldNotInsertThatEmployee(){
        val employees = listOf(null)
        Truth.assertThat(dataSource.insertEmployees(employees)).isNull()
    }


    @Test
    fun getInsertEmployee_whenInsertNullEmployeeWithValidEmployee_shouldNotInsertOnlyNullEmployees(){
        val employee = Employee(id = 1,name = "Fahim",salary = 2000.0f,age = 21,rating = 5.0f)
        val employees = listOf(employee,null)
        Truth.assertThat(dataSource.insertEmployees(employees)).hasSize(1)
    }

    @Test
    fun getSearchByName_whenSearchDataWithValidString_shouldReturnEmployeeList(){
        val employees = listOf(employee)
        dataSource.insertEmployees(employees)
        val actualList = TestUtils.getValue(dataSource.employeesSortByName(name).toLiveData(Config(pageSize = 30,enablePlaceholders = false,maxSize = 1000)))
        Truth.assertThat(actualList).contains(employee)
    }


    @Test
    fun getSearchByName_whenSearchNameIsNull_shouldReturnAllEmployee(){
        val employees = listOf(employee)
        dataSource.insertEmployees(employees)
        val actualList = TestUtils.getValue(dataSource.employeesSortByName(null).toLiveData(Config(pageSize = 30,enablePlaceholders = false,maxSize = 1000)))
        Truth.assertThat(actualList).contains(employee)
    }

    @Test
    fun getUpdateEmployee_whenUpdateValidEmployee_shouldReturnTrue(){
        val  list = dataSource.insertEmployees(listOf(employee))
        val updatedEmployee = employee
        updatedEmployee.uid = list!![0].toInt()
        Truth.assertThat(dataSource.updateEmployee(updatedEmployee)).isTrue()
    }

    @Test
    fun getUpdateEmployee_whenUpdateInvalidEmployee_shouldReturnFalse(){
        Truth.assertThat(dataSource.updateEmployee(employee)).isFalse()
    }

    @Test
    fun getUpdateEmployee_whenUpdateNull_shouldReturnFalse(){
        Truth.assertThat(dataSource.updateEmployee(null)).isFalse()
    }

    @Test
    fun getUpdateRating_whenUpdateValidInfo_shouldReturnTrue(){
        val  list = dataSource.insertEmployees(listOf(employee))
        Truth.assertThat(dataSource.updateRating(list!![0].toInt(),2.3f)).isTrue()
    }

    @Test
    fun getUpdateRating_whenUpdateInvalidInfo_shouldReturnFalse(){
        Truth.assertThat(dataSource.updateRating(1,2.3f)).isFalse()
    }

    @Test
    fun getDeleteEmployee_whenDeleteValidEmployee_shouldReturnTrue(){
        val  list = dataSource.insertEmployees(listOf(employee))
        val deletedEmployee = employee
        deletedEmployee.uid = list!![0].toInt()
        Truth.assertThat(dataSource.deleteEmployee(deletedEmployee)).isTrue()
    }

    @Test
    fun getDeleteEmployee_whenDeleteInvalidEmployee_shouldReturnFalse(){
        Truth.assertThat(dataSource.deleteEmployee(employee)).isFalse()
    }

    @Test
    fun getDeleteEmployee_whenDeleteNull_shouldReturnFalse(){
        Truth.assertThat(dataSource.deleteEmployee(null)).isFalse()
    }
}