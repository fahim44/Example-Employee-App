package com.fahim.example_employee_app

import com.fahim.example_employee_app.model.Employee
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object AndroidTestEmployeeFactory {
    private fun makeRandomString() = UUID.randomUUID().toString()

    private fun makeRandomInt() =
        ThreadLocalRandom.current().nextInt(0,1000)

    private fun makeRandomFloat(origin:Double = 0.0, bound:Double = 1000.0) =
        ThreadLocalRandom.current().nextDouble(origin,bound).toFloat()

    fun makeEmployee() : Employee {
        return Employee(makeRandomInt(), makeRandomString(), makeRandomFloat(), makeRandomInt(),
            makeRandomFloat(bound = 5.0))
    }

    fun makeEmployeeList() : List<Employee>{
        return listOf(Employee(makeRandomInt(), makeRandomString(), makeRandomFloat(), makeRandomInt(),
            makeRandomFloat(bound = 5.0)))
    }
}