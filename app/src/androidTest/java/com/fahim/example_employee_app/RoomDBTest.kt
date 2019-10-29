package com.fahim.example_employee_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.fahim.example_employee_app.models.Employee
import com.fahim.example_employee_app.room.EmployeeDao
import com.fahim.example_employee_app.room.EmployeeDatabase
import org.junit.After

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


class RoomDBTest {
    private lateinit var dao : EmployeeDao
    private lateinit var db : EmployeeDatabase


    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(context,EmployeeDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.employeeDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown(){
        db.close()
    }


    @Test
    fun testCreate(){
        val expected = Employee(1,"fahim",100000.99f,21,5.0f)
        val id = dao.insert(expected)

        val ld = dao.getEmployee(id[0].toInt()).blockingObserve()

        assertEquals(expected.age, ld!!.age)
    }


    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value : T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }
        observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        return value
    }
}