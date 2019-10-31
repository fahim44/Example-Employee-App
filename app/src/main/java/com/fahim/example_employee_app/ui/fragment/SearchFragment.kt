package com.fahim.example_employee_app.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.adapter.EmployeeListAdapter
import com.fahim.example_employee_app.model.Employee
import com.fahim.example_employee_app.ui.activity.DetailActivity
import com.fahim.example_employee_app.util.EmployeeKeys
import com.fahim.example_employee_app.viewmodel.SearchViewModel
import javax.inject.Inject

class SearchFragment : Fragment() {


    private var recyclerView: RecyclerView? = null
    private  var adapter: EmployeeListAdapter? = null
    private  var observer: Observer<PagedList<Employee>>? = null
    private var currentSearchedName = ""

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val searchViewModel : SearchViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as EmployeeApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        initView(root)
        handleLiveData()
        return root
    }

    private fun initView(root:View){
        recyclerView = root.findViewById(R.id.rv) as RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)

        val et : EditText = root.findViewById(R.id.et_search)
        val ib : ImageButton = root.findViewById(R.id.ib_search)
        ib.setOnClickListener {
            hideKeyboard()
            if (et.text != null && et.text.toString().trim()!="" && et.text.toString() != currentSearchedName){
                adapter  = EmployeeListAdapter(searchViewModel,R.layout.list_item_layout)
                observer?.let { searchViewModel.searchedEmployeeListLD?.removeObserver(it) }
                observer = Observer(adapter!!::submitList)
                searchViewModel.search("%" + et.text.toString() + "%")
                searchViewModel.searchedEmployeeListLD?.observe(this,observer!!)
                recyclerView?.adapter = adapter
                currentSearchedName = et.text.toString()
            } }
    }

    private fun handleLiveData(){
        searchViewModel.navigateToDetailActivityLD.observe(this, Observer {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(EmployeeKeys.EMPLOYEE_ID,it)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        })
    }

    private fun hideKeyboard() {
        val view = activity?.currentFocus
        view?.let { v ->
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}