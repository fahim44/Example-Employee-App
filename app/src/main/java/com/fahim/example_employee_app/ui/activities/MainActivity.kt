package com.fahim.example_employee_app.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.utils.SharedPreference
import com.fahim.example_employee_app.viewmodels.MainActivityViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var preference : SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java).show()
    }


}
