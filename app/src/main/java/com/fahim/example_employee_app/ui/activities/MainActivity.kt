package com.fahim.example_employee_app.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.viewmodels.MainActivityViewModel
class MainActivity : AppCompatActivity() {

    private var viewModel : MainActivityViewModel ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel?.checkExistingData()

        handleLiveData()
    }

    private fun handleLiveData(){
        viewModel?.dummyDataSetLD?.observe(this, Observer {
            it?.let {
                viewModel?.setPrefInitDataValue(it)
            } })

        viewModel?.navigateToTabActivityLD?.observe(this, Observer {
            it?.let {
                if(it)
                    startActivity(Intent(this,TabPageActivity::class.java))
                    finish()
            } })
    }

}
