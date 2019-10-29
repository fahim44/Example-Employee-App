package com.fahim.example_employee_app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.adapters.EmployeeListAdapter
import com.fahim.example_employee_app.ui.activities.DetailActivity
import com.fahim.example_employee_app.utils.EmployeeKeys
import com.fahim.example_employee_app.viewmodels.ListViewModel

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        listViewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_list, container, false)
        val adapter = EmployeeListAdapter(listViewModel)
        listViewModel.allEmployeeListLD.observe(this, Observer(adapter::submitList))
        val recyclerView = root.findViewById(R.id.rv) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        listViewModel.navigateToDetailActivityLD.observe(this, Observer {
            val intent = Intent(activity,DetailActivity::class.java)
            intent.putExtra(EmployeeKeys.EMPLOYEE_ID,it)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        })

        return root
    }
}