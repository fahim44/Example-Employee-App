package com.fahim.example_employee_app.ui.activity


import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fahim.example_employee_app.databinding.ActivityDetailBinding
import com.fahim.example_employee_app.util.EmployeeKeys
import com.fahim.example_employee_app.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject
import android.content.Intent
import com.fahim.example_employee_app.R
import dagger.android.support.DaggerAppCompatActivity


class DetailActivity : DaggerAppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel : DetailViewModel by viewModels {
        viewModelFactory
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.detaiil_page)

        if (intent.hasExtra(EmployeeKeys.EMPLOYEE_ID)) {
            viewModel.uid = intent.getIntExtra(EmployeeKeys.EMPLOYEE_ID,-1)
            if(viewModel.uid>=0) {
                viewModel.getEmployee().observe(this, Observer {
                    binding.obj = it
                }) }
            else
                startTabPageActivity()
        }else
            startTabPageActivity()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        viewModel.updateRating(rating_bar.rating)
        startTabPageActivity()
    }


    private fun startTabPageActivity(){
        val myIntent = Intent(this, TabPageActivity::class.java)
        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(myIntent)
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
        finish()
    }
}
