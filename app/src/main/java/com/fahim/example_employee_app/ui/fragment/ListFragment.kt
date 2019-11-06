package com.fahim.example_employee_app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.adapter.EmployeeListAdapter
import com.fahim.example_employee_app.viewmodel.ListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var adapter : EmployeeListAdapter

    private val listViewModel : ListViewModel by viewModels {
        viewModelFactory
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
            val action = ListFragmentDirections.actionListToDetail().setUid(it)
            findNavController().navigate(action)
        })
    }
}