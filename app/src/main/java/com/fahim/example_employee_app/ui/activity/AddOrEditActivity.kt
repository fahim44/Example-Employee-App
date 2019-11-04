package com.fahim.example_employee_app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.databinding.ActivityAddOrEditBinding
import com.fahim.example_employee_app.util.EmployeeKeys
import com.fahim.example_employee_app.viewmodel.AddOrEditViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_add_or_edit.*
import javax.inject.Inject

class AddOrEditActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel : AddOrEditViewModel by viewModels {
        viewModelFactory
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityAddOrEditBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_add_or_edit)

        handleLiveData()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /// edit employee
        if (intent.hasExtra(EmployeeKeys.EMPLOYEE_ID)) {
            viewModel.uid = intent.getIntExtra(EmployeeKeys.EMPLOYEE_ID,-1)

            supportActionBar?.title = getString(R.string.edit_page)

            viewModel.getEmployee(viewModel.uid).observe(this, Observer {
                binding.obj = it
                viewModel.rating = it.rating
            })
        }
        // add new employee
        else {
            supportActionBar?.title = getString(R.string.add_page)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_edit_activity,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        } else if (item.itemId == R.id.menu_done){
            val name = et_name.text.toString()
            val id = et_id.text.toString()
            val age = et_age.text.toString()
            val salary = et_salary.text.toString()
            viewModel.doneButtonPressed(name,id,age,salary)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val myIntent = Intent(this, TabPageActivity::class.java)
        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(myIntent)
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
        finish()

    }

    private fun handleLiveData() {
        viewModel.toastLD.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })
        viewModel.onBackPressLD.observe(this, Observer {
            if(it)
                onBackPressed()
        })
    }
}