package com.fahim.example_employee_app.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahim.example_employee_app.EmployeeApplication
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.adapters.EmployeeListAdapter
import com.fahim.example_employee_app.ui.activities.AddOrEditActivity
import com.fahim.example_employee_app.utils.EmployeeKeys
import com.fahim.example_employee_app.viewmodels.RemoveListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class RemoveListFragment : Fragment() {

    private var recyclerView :RecyclerView? = null
    private lateinit var adapter: EmployeeListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val removeListViewModel : RemoveListViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as EmployeeApplication).component.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
                showDeleteDialog(viewHolder.adapterPosition)
            }
        }
        ).attachToRecyclerView(recyclerView)
    }


    private fun showDeleteDialog(adapterPosition:Int){
        val builder = AlertDialog.Builder(context!!)
        builder.setCancelable(false)
        builder.setMessage("Do you want to delete this employee?")
        builder.setPositiveButton("Yes"){_, _ ->
            removeListViewModel.delete(adapter.getEmployeeAt(adapterPosition))
            Toast.makeText(context, "Employee Deleted!", Toast.LENGTH_SHORT).show() }
        builder.setNegativeButton("No"){dialog, _ ->
            adapter.notifyItemChanged(adapterPosition)
            dialog.cancel() }
        builder.create().show()
    }
}