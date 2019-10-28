package com.fahim.example_employee_app.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "employee")
data class Employee (
    @PrimaryKey val id : Int,
    @ColumnInfo(name ="employee_name") @SerializedName("employee_name") val name : String,
    @ColumnInfo(name = "employee_salary") @SerializedName("employee_salary") val salary : String,
    @ColumnInfo(name="employee_age") @SerializedName("employee_age") val age : Int,
    @ColumnInfo(name="profile_image") @SerializedName("profile_image") val image : String,
    @ColumnInfo(name="employee_rating") @SerializedName("employee_rating") val rating : Int
)