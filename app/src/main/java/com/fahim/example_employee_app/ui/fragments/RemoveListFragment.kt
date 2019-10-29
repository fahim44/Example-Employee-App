package com.fahim.example_employee_app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.adapters.EmployeeListAdapter
import com.fahim.example_employee_app.ui.activities.AddOrEditActivity
import com.fahim.example_employee_app.utils.EmployeeKeys
import com.fahim.example_employee_app.viewmodels.RemoveListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RemoveListFragment : Fragment() {

    private lateinit var removeListViewModel: RemoveListViewModel
    private var recyclerView :RecyclerView? = null
    private lateinit var adapter: EmployeeListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        removeListViewModel = ViewModelProviders.of(this).get(RemoveListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_remove_list, container, false)

        adapter = EmployeeListAdapter(removeListViewModel,R.layout.remove_list_item_layout)
        removeListViewModel.allEmployeeListLD.observe(this, Observer(adapter::submitList))
        recyclerView = root.findViewById(R.id.rv) as RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

        root.findViewById<FloatingActionButton>(R.id.fab_add).setOnClickListener {
            val intent = Intent(activity, AddOrEditActivity::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        }

        removeListViewModel.navigateToAddEditActivityLD.observe(this, Observer {
            val intent = Intent(activity,AddOrEditActivity::class.java)
            intent.putExtra(EmployeeKeys.EMPLOYEE_ID,it)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        })

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
                removeListViewModel.delete(adapter.getEmployeeAt(viewHolder.adapterPosition))
                Toast.makeText(context, "Employee Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recyclerView)

    }

}