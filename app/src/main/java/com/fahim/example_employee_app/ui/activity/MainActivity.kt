package com.fahim.example_employee_app.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.viewmodel.MainActivityViewModel
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel : MainActivityViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as EmployeeApplication).component.inject(this)
        supportActionBar?.hide()
        //viewModel = ViewModelProviders.of(this,viewModelFactory).get(MainActivityViewModel::class.java)
        //viewModel = createViewModel { (application as EmployeeApplication).component.provideMainViewModel() }
        viewModel.checkExistingData()

        handleLiveData()
    }

    private fun handleLiveData(){
        viewModel.dummyDataSetLD.observe(this, Observer {
            it?.let {
                viewModel.setPrefInitDataValue(it)
            } })

        viewModel.navigateToTabActivityLD.observe(this, Observer {
            it?.let {
                if(it)
                    startActivity(Intent(this,TabPageActivity::class.java))
                    finish()
            } })
    }

}
