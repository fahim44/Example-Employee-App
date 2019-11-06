package com.fahim.example_employee_app.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.adapter.EmployeeListAdapter
import com.fahim.example_employee_app.viewmodel.RemoveListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RemoveListFragment : DaggerFragment() {

    private var recyclerView :RecyclerView? = null
    private lateinit var adapter: EmployeeListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val removeListViewModel : RemoveListViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_remove_list, container, false)
        initView(root)
        handleLiveData()
        setSwipeFunctionality()
        return root
    }

    private fun initView(root:View){
        adapter = EmployeeListAdapter(removeListViewModel,R.layout.remove_list_item_layout)
        recyclerView = root.findViewById(R.id.rv) as RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

        root.findViewById<FloatingActionButton>(R.id.fab_add).setOnClickListener {
            val action = RemoveListFragmentDirections.actionAddOrEdit()
            findNavController().navigate(action)
        }
    }

    private fun handleLiveData(){
        removeListViewModel.allEmployeeListLD.observe(this, Observer(adapter::submitList))

        removeListViewModel.navigateToAddEditActivityLD.observe(this, Observer {
            val action = RemoveListFragmentDirections.actionAddOrEdit().setUid(it)
            findNavController().navigate(action)
        })
    }

    private fun setSwipeFunctionality(){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder)
                    : Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showDeleteDialog(viewHolder.adapterPosition)
            } }).attachToRecyclerView(recyclerView)
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