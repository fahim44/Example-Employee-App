package com.fahim.example_employee_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.adapters.EmployeeListAdapter
import com.fahim.example_employee_app.models.Employee
import com.fahim.example_employee_app.viewmodels.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    private var recyclerView: RecyclerView? = null
    private  var adapter: EmployeeListAdapter? = null
    private  var observer: Observer<PagedList<Employee>>? = null
    private var currentSearchedName = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = root.findViewById(R.id.rv) as RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)

        val et : EditText = root.findViewById(R.id.et_search)
        val ib : ImageButton = root.findViewById(R.id.ib_search)
        ib.setOnClickListener {
            if (et.text != null && et.text.toString().trim()!="" && et.text.toString() != currentSearchedName){
                adapter  = EmployeeListAdapter(searchViewModel)
                observer?.let {
                    searchViewModel.searchedEmployeeListLD?.removeObserver(it)
                }
                observer = Observer(adapter!!::submitList)
                searchViewModel.search("%" + et.text.toString() + "%")
                searchViewModel.searchedEmployeeListLD?.observe(this,observer!!)
                recyclerView?.adapter = adapter
                currentSearchedName = et.text.toString()
            }
        }

        return root
    }
}