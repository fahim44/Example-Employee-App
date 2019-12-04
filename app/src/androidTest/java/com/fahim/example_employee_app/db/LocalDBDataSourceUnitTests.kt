package com.fahim.example_employee_app.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.Config
import androidx.paging.toLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fahim.example_employee_app.AndroidTestEmployeeFactory
import com.fahim.example_employee_app.TestUtils
import com.google.common.truth.Truth
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalDBDataSourceUnitTests {
    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private lateinit var dataSource : LocalDBDataSource
    private lateinit var db : EmployeeDatabase


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
        Truth.assertThat(dataSource.insertEmployees(AndroidTestEmployeeFactory.makeEmployeeList())).hasSize(1)
    }

    @Test
    fun getInsertEmployee_whenInsertingNullEmployeeList_shouldNotInsertIntoDB(){
        Truth.assertThat(dataSource.insertEmployees(null)).isNull()
    }

    @Test
    fun getInsertEmployee_whenInsertNullEmployee_shouldNotInsertThatEmployee(){
        Truth.assertThat(dataSource.insertEmployees(listOf(null))).isNull()
    }


    @Test
    fun getInsertEmployee_whenInsertNullEmployeeWithValidEmployee_shouldNotInsertOnlyNullEmployees(){
        Truth.assertThat(dataSource.insertEmployees(AndroidTestEmployeeFactory.makeEmployeeList())).hasSize(1)
    }

    @Test
    fun getSearchByName_whenSearchDataWithValidString_shouldReturnEmployeeList(){
        val employees = AndroidTestEmployeeFactory.makeEmployeeList()
        val name = employees[0].name
        dataSource.insertEmployees(employees)
        val actualList = TestUtils.getValue(dataSource.employeesSortByName(name).toLiveData(Config(pageSize = 30,enablePlaceholders = false,maxSize = 1000)))
        Truth.assertThat(actualList).contains(employees[0])
    }


    @Test
    fun getSearchByName_whenSearchNameIsNull_shouldReturnAllEmployee(){
        val employees = AndroidTestEmployeeFactory.makeEmployeeList()
        dataSource.insertEmployees(employees)
        val actualList = TestUtils.getValue(dataSource.employeesSortByName(null).toLiveData(Config(pageSize = 30,enablePlaceholders = false,maxSize = 1000)))
        Truth.assertThat(actualList).contains(employees[0])
    }

    @Test
    fun getUpdateEmployee_whenUpdateValidEmployee_shouldReturnTrue(){
        val updatedEmployee = AndroidTestEmployeeFactory.makeEmployee()
        val  list = dataSource.insertEmployees(listOf(updatedEmployee))
        updatedEmployee.uid = list!![0].toInt()
        Truth.assertThat(dataSource.updateEmployee(updatedEmployee)).isTrue()
    }

    @Test
    fun getUpdateEmployee_whenUpdateInvalidEmployee_shouldReturnFalse(){
        Truth.assertThat(dataSource.updateEmployee(AndroidTestEmployeeFactory.makeEmployee())).isFalse()
    }

    @Test
    fun getUpdateEmployee_whenUpdateNull_shouldReturnFalse(){
        Truth.assertThat(dataSource.updateEmployee(null)).isFalse()
    }

    @Test
    fun getUpdateRating_whenUpdateValidInfo_shouldReturnTrue(){
        val employee = AndroidTestEmployeeFactory.makeEmployee()
        val  list = dataSource.insertEmployees(listOf(employee))
        Truth.assertThat(dataSource.updateRating(list!![0].toInt(),employee.rating)).isTrue()
    }

    @Test
    fun getUpdateRating_whenUpdateInvalidInfo_shouldReturnFalse(){
        Truth.assertThat(dataSource.updateRating(1,2.3f)).isFalse()
    }

    @Test
    fun getDeleteEmployee_whenDeleteValidEmployee_shouldReturnTrue(){
        val deletedEmployee = AndroidTestEmployeeFactory.makeEmployee()
        val  list = dataSource.insertEmployees(listOf(deletedEmployee))
        deletedEmployee.uid = list!![0].toInt()
        Truth.assertThat(dataSource.deleteEmployee(deletedEmployee)).isTrue()
    }

    @Test
    fun getDeleteEmployee_whenDeleteInvalidEmployee_shouldReturnFalse(){
        Truth.assertThat(dataSource.deleteEmployee(AndroidTestEmployeeFactory.makeEmployee())).isFalse()
    }

    @Test
    fun getDeleteEmployee_whenDeleteNull_shouldReturnFalse(){
        Truth.assertThat(dataSource.deleteEmployee(null)).isFalse()
    }
}