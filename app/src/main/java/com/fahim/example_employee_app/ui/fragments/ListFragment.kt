package com.fahim.example_employee_app.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.adapters.EmployeeListAdapter
import com.fahim.example_employee_app.ui.activities.DetailActivity
import com.fahim.example_employee_app.utils.EmployeeKeys
import com.fahim.example_employee_app.viewmodels.ListViewModel
import com.fahim.example_employee_app.viewmodels.MainActivityViewModel
import javax.inject.Inject

class ListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var adapter : EmployeeListAdapter

    private val listViewModel : ListViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as EmployeeApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        initView(root)
        handleLiveData()
        return root
    }

    private fun initView(root:View){
        adapter = EmployeeListAdapter(listViewModel,R.layout.list_item_layout)

        val recyclerView = root.findViewById(R.id.rv) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun handleLiveData(){
        listViewModel.allEmployeeListLD.observe(this, Observer(adapter::submitList))

        listViewModel.navigateToDetailActivityLD.observe(this, Observer {
            val intent = Intent(activity,DetailActivity::class.java)
            intent.putExtra(EmployeeKeys.EMPLOYEE_ID,it)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        })
    }
}