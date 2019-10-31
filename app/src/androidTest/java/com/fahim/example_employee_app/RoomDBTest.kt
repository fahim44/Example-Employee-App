package com.fahim.example_employee_app

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class RoomDBTest {
    @get:Rule val testRule = InstantTaskExecutorRule()

    companion object {
        fun <T> getValue(liveData: LiveData<T>): T {
            val data = arrayOfNulls<Any>(1)
            val latch = CountDownLatch(1)
            val observer = object : Observer<T> {
                override fun onChanged(o: T?) {
                    data[0] = o
                    latch.countDown()
                    liveData.removeObserver(this)
                }
            }
            liveData.observeForever(observer)
            latch.await(2, TimeUnit.SECONDS)

            @Suppress("UNCHECKED_CAST")
            return data[0] as T
        }
    }


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
    fun insertData(){
        val expected = Employee(1,"fahim",100000.99f,21,5.0f)
        val id = dao.insert(expected)
        val actual = getValue(dao.getEmployee(id[0].toInt()))

        assertEquals(expected.name, actual.name)
        assertEquals(expected.id, actual.id)
        assertEquals(expected.salary,actual.salary)
        assertEquals(expected.rating,actual.rating)
        assertEquals(expected.age,actual.age)
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
        val actual = getValue(dao.getEmployee(id[0].toInt()))

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
        val actual = getValue(dao.getEmployee(id[0].toInt()))

        assertNull(actual)
    }


}