package com.fahim.example_employee_app

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.Config
import androidx.paging.toLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.db.EmployeeDao
import com.fahim.example_employee_app.db.EmployeeDatabase
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class EmployeeDatabaseTest {
    @get:Rule val testRule = InstantTaskExecutorRule()

    private lateinit var dao : EmployeeDao
    private lateinit var db : EmployeeDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context,EmployeeDatabase::class.java)
            .build()
        dao = db.employeeDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown(){
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertData(){   //insert and get data test
        val expected = Employee(1,"fahim",100000.99f,21,5.0f)
        val id = dao.insert(expected)
        val actual = TestUtils.getValue(dao.getEmployee(id[0].toInt()))

        assertEquals(expected.name, actual.name)
        assertEquals(expected.id, actual.id)
        assertEquals(expected.salary,actual.salary)
        assertEquals(expected.rating,actual.rating)
        assertEquals(expected.age,actual.age)
    }

    @Test
    fun insertMultipleData(){
        val expected1 = Employee(1,"fahim",100000.99f,21,5.0f)

        val expected2 = Employee(2,"rahul",10.93f,35,0.23f)

        val expected3 = Employee(3,"piash",1000.65f,10,3.4f)

        dao.insert(expected3,expected1,expected2)

        val actualList = TestUtils.getValue(dao.getAllEmployees().toLiveData(Config(pageSize = 30,enablePlaceholders = false,maxSize = 1000)))

        assertEquals(3,actualList.size)

        assertEquals(expected1.name, actualList[0]?.name)
        assertEquals(expected1.id, actualList[0]?.id)
        assertEquals(expected1.salary,actualList[0]?.salary)
        assertEquals(expected1.rating,actualList[0]?.rating)
        assertEquals(expected1.age,actualList[0]?.age)

        assertEquals(expected2.name, actualList[1]?.name)
        assertEquals(expected2.id, actualList[1]?.id)
        assertEquals(expected2.salary,actualList[1]?.salary)
        assertEquals(expected2.rating,actualList[1]?.rating)
        assertEquals(expected2.age,actualList[1]?.age)

        assertEquals(expected3.name, actualList[2]?.name)
        assertEquals(expected3.id, actualList[2]?.id)
        assertEquals(expected3.salary,actualList[2]?.salary)
        assertEquals(expected3.rating,actualList[2]?.rating)
        assertEquals(expected3.age,actualList[2]?.age)
    }


    @Test
    @Throws(Exception::class)
    fun updateData(){
        val expected = Employee(1,"fahim",100000.99f,21,5.0f)
        val id = dao.insert(expected)
        expected.rating = 0.3f
        expected.name = "salam"
        expected.age = 27
        expected.id = 44
        expected.salary = 223355.887f
        expected .uid = id[0].toInt()
        dao.update(expected)
        val actual = TestUtils.getValue(dao.getEmployee(id[0].toInt()))

        assertEquals(expected.name, actual.name)
        assertEquals(expected.id, actual.id)
        assertEquals(expected.salary,actual.salary)
        assertEquals(expected.rating,actual.rating)
        assertEquals(expected.age,actual.age)
    }

    @Test
    @Throws(Exception::class)
    fun deleteData(){
        val expected = Employee(1,"fahim",100000.99f,21,5.0f)
        val id = dao.insert(expected)
        expected.uid = id[0].toInt()
        dao.delete(expected)
        val actual = TestUtils.getValue(dao.getEmployee(id[0].toInt()))

        assertNull(actual)
    }


    @Test
    fun updateRating(){
        val insertedValue = Employee(1,"fahim",100000.99f,21,5.0f)
        val id = dao.insert(insertedValue)
        val rating = 2.01f
        dao.updateRating(id[0].toInt(),rating)
        val actual = TestUtils.getValue(dao.getEmployee(id[0].toInt()))

        assertNotEquals(insertedValue.rating,actual.rating)
        assertEquals(rating,actual.rating)
    }

    @Test
    fun getAllData(){
        val expected1 = Employee(1,"fahim",100000.99f,21,5.0f)
        val id1 = dao.insert(expected1)
        expected1.uid = id1[0].toInt()

        val expected2 = Employee(2,"rahul",10.93f,35,0.23f)
        val id2 = dao.insert(expected2)
        expected2.uid = id2[0].toInt()

        val expected3 = Employee(3,"piash",1000.65f,10,3.4f)
        val id3 = dao.insert(expected3)
        expected3.uid = id3[0].toInt()


        val actualList = TestUtils.getValue(dao.getAllEmployees().toLiveData(Config(pageSize = 30,enablePlaceholders = false,maxSize = 1000)))

        assertEquals(3,actualList.size)

        assertEquals(expected1.uid, actualList[0]?.uid)
        assertEquals(expected1.name, actualList[0]?.name)
        assertEquals(expected1.id, actualList[0]?.id)
        assertEquals(expected1.salary,actualList[0]?.salary)
        assertEquals(expected1.rating,actualList[0]?.rating)
        assertEquals(expected1.age,actualList[0]?.age)

        assertEquals(expected2.uid, actualList[1]?.uid)
        assertEquals(expected2.name, actualList[1]?.name)
        assertEquals(expected2.id, actualList[1]?.id)
        assertEquals(expected2.salary,actualList[1]?.salary)
        assertEquals(expected2.rating,actualList[1]?.rating)
        assertEquals(expected2.age,actualList[1]?.age)

        assertEquals(expected3.uid, actualList[2]?.uid)
        assertEquals(expected3.name, actualList[2]?.name)
        assertEquals(expected3.id, actualList[2]?.id)
        assertEquals(expected3.salary,actualList[2]?.salary)
        assertEquals(expected3.rating,actualList[2]?.rating)
        assertEquals(expected3.age,actualList[2]?.age)
    }


    @Test
    fun getSearchedData(){
        val expected1 = Employee(1,"abcd",100000.99f,21,2.4f)
        val id1 = dao.insert(expected1)
        expected1.uid = id1[0].toInt()

        val expected2 = Employee(2,"bcf",10.93f,35,4.3f)
        val id2 = dao.insert(expected2)
        expected2.uid = id2[0].toInt()

        val expected3 = Employee(3,"abeeeec",1000.65f,10,5.0f)
        val id3 = dao.insert(expected3)
        expected3.uid = id3[0].toInt()


        val actualList = TestUtils.getValue(dao.employeesSortByName("%bc%").toLiveData(Config(pageSize = 30,enablePlaceholders = false,maxSize = 1000)))

        assertEquals(2,actualList.size)

        assertFalse(actualList.contains(expected3))
        assertTrue(actualList.contains(expected1))
        assertTrue(actualList.contains(expected2))

        assertEquals(expected2.uid, actualList[0]?.uid)
        assertEquals(expected2.name, actualList[0]?.name)
        assertEquals(expected2.id, actualList[0]?.id)
        assertEquals(expected2.salary,actualList[0]?.salary)
        assertEquals(expected2.rating,actualList[0]?.rating)
        assertEquals(expected2.age,actualList[0]?.age)

        assertEquals(expected1.uid, actualList[1]?.uid)
        assertEquals(expected1.name, actualList[1]?.name)
        assertEquals(expected1.id, actualList[1]?.id)
        assertEquals(expected1.salary,actualList[1]?.salary)
        assertEquals(expected1.rating,actualList[1]?.rating)
        assertEquals(expected1.age,actualList[1]?.age)
    }

}