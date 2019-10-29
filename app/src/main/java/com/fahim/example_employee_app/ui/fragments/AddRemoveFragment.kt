package com.fahim.example_employee_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.adapters.EmployeeListAdapter
import com.fahim.example_employee_app.viewmodels.AddRemoveViewModel

class AddRemoveFragment : Fragment() {

    private lateinit var addRemoveViewModel: AddRemoveViewModel
    private var recyclerView :RecyclerView? = null
    private lateinit var adapter: EmployeeListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        addRemoveViewModel = ViewModelProviders.of(this).get(AddRemoveViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_remove, container, false)

        adapter = EmployeeListAdapter(addRemoveViewModel,R.layout.add_remove_list_item_layout)
        addRemoveViewModel.allEmployeeListLD.observe(this, Observer(adapter::submitList))
        recyclerView = root.findViewById(R.id.rv) as RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

        setSwipeFunctionality()

        return root
    }

    private fun setSwipeFunctionality(){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder)
                    : Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                addRemoveViewModel.delete(adapter.getEmployeeAt(viewHolder.adapterPosition))
                Toast.makeText(context, "Employee Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recyclerView)

    }

}