package com.fahim.example_employee_app.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.databinding.ActivityAddOrEditBinding
import com.fahim.example_employee_app.utils.EmployeeKeys
import com.fahim.example_employee_app.viewmodels.AddOrEditViewModel
import kotlinx.android.synthetic.main.activity_add_or_edit.*
import javax.inject.Inject

class AddOrEditActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel : AddOrEditViewModel by viewModels {
        viewModelFactory
    }

    // if uid < 0, then add, if uid>=0, then edit employee
    private var uid = -1
    private var rating = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as EmployeeApplication).component.inject(this)

        val binding: ActivityAddOrEditBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_add_or_edit)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        /// edit employee
        if (intent.hasExtra(EmployeeKeys.EMPLOYEE_ID)) {
            uid = intent.getIntExtra(EmployeeKeys.EMPLOYEE_ID,0)

            supportActionBar?.title = getString(R.string.edit_page)

            viewModel.getEmployee(uid).observe(this, Observer {
                binding.obj = it
                rating = it.rating
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
            if(viewModel.validateInput(name,id,age,salary)){
                viewModel.addOrEditEmployee(uid,name,id,age,salary,rating)
                Toast.makeText(this,"Success!!!",Toast.LENGTH_SHORT).show()
                onBackPressed()
            }else{
                Toast.makeText(this,"Please input valid info",Toast.LENGTH_SHORT).show()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val myIntent = Intent(this, TabPageActivity::class.java)
        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(myIntent)
        finish()
    }
}