package com.fahim.example_employee_app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.databinding.FragmentDetailBinding
import com.fahim.example_employee_app.viewmodel.DetailViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

class DetailFragment : DaggerFragment(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val args : DetailFragmentArgs by navArgs()

    private val viewModel : DetailViewModel by viewModels {
        viewModelFactory
    }

    private var uid = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail,container,false)

        uid = args.uid
        if(uid>=0)
            viewModel.uid = uid
            viewModel.getEmployee().observe(this, Observer {
                binding.obj = it
            })
        return binding.root
    }


    override fun onPause() {

        viewModel.updateRating(rating_bar.rating)
        super.onPause()
    }
}