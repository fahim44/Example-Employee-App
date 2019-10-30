package com.fahim.example_employee_app.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.databinding.ActivityDetailBinding
import com.fahim.example_employee_app.utils.EmployeeKeys
import com.fahim.example_employee_app.viewmodels.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject
import android.content.Intent
import com.fahim.example_employee_app.R


class DetailActivity : AppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel : DetailViewModel by viewModels {
        viewModelFactory
    }

    private var uid = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as EmployeeApplication).component.inject(this)

        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.detaiil_page)

        if (intent.hasExtra(EmployeeKeys.EMPLOYEE_ID)) {
            uid = intent.getIntExtra(EmployeeKeys.EMPLOYEE_ID,0)
            viewModel.getEmployee(uid).observe(this, Observer {
                binding.obj = it
            })
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(uid>=0)
            viewModel.updateRating(uid, rating_bar.rating)
        val myIntent = Intent(this, TabPageActivity::class.java)
        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(myIntent)
        finish()
    }
}
