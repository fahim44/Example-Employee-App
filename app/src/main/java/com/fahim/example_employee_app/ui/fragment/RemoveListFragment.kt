package com.fahim.example_employee_app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahim.example_employee_app.R
import com.fahim.example_employee_app.adapter.EmployeeListAdapter
import com.fahim.example_employee_app.callBack.AlertDialogCallBack
import com.fahim.example_employee_app.util.ViewUtils
import com.fahim.example_employee_app.viewmodel.RemoveListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RemoveListFragment : DaggerFragment() {

    private var recyclerView :RecyclerView? = null
    private lateinit var adapter: EmployeeListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel : RemoveListViewModel by viewModels {
        viewModelFactory
    }

    @Inject lateinit var viewUtils: ViewUtils

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_remove_list, container, false)
        initView(root)
        handleLiveData()
        setSwipeFunctionality()
        return root
    }

    private fun initView(root:View){
        adapter = EmployeeListAdapter(viewModel,R.layout.remove_list_item_layout)
        recyclerView = root.findViewById(R.id.rv) as RecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter

        root.findViewById<FloatingActionButton>(R.id.fab_add).setOnClickListener {
            val action = RemoveListFragmentDirections.actionAddOrEdit()
            findNavController().navigate(action)
        }
    }

    private fun handleLiveData(){
        viewModel.allEmployeeListLD.observe(this, Observer(adapter::submitList))

        viewModel.navigateToAddEditActivityLD.observe(this, Observer {
            val action = RemoveListFragmentDirections.actionAddOrEdit().setUid(it)
            findNavController().navigate(action)
        })

        viewModel.notifyAdapterItemChangedLD.observe(this, Observer {
            adapter.notifyItemChanged(it)
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
        viewUtils.showAlertDialog(null,getString(R.string.delete_employee_confirmation),
            (ViewUtils.SHOW_POSITIVE_BUTTON or ViewUtils.SHOW_NEGATIVE_BUTTON),
            object : AlertDialogCallBack{
                override fun onClickPositiveButton() {
                    viewModel.delete(adapter.getEmployeeAt(adapterPosition),adapterPosition)
                }
                override fun onClickNegativeButton() { adapter.notifyItemChanged(adapterPosition) }
        })
    }
}