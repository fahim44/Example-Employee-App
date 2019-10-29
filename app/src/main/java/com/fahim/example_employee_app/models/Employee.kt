package com.fahim.example_employee_app.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "employee")
data class Employee (
    var id : Int,
    @SerializedName("employee_name") @ColumnInfo(name ="employee_name") var name : String,
    @SerializedName("employee_salary") @ColumnInfo(name = "employee_salary") var salary : Float,
    @SerializedName("employee_age") @ColumnInfo(name="employee_age") var age : Int,
    //@SerializedName("profile_image") @ColumnInfo(name="profile_image") var image : String,
    @SerializedName("employee_rating") @ColumnInfo(name="employee_rating") var rating : Float
){
    @PrimaryKey(autoGenerate = true) var uid : Int=0
}