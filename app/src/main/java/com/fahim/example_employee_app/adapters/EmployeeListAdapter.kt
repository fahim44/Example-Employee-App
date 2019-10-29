package com.fahim.example_employee_app.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.fahim.example_employee_app.models.Employee
import com.fahim.example_employee_app.viewmodels.BaseViewModel
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import com.fahim.example_employee_app.R


class EmployeeListAdapter(private val viewModel: BaseViewModel, private val item_layout_id:Int)
    : PagedListAdapter<Employee,EmployeeListAdapter.EmployeeViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate(layoutInflater, item_layout_id, parent, false) as ViewDataBinding
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = getItem(position)
        holder.bind(employee,viewModel)
    }

    fun getEmployeeAt(position : Int) : Employee? = getItem(position)

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Employee>() {
            override fun areItemsTheSame(oldEmployee: Employee,
                                         newEmployee: Employee) = oldEmployee.uid == newEmployee.uid

            override fun areContentsTheSame(oldEmployee: Employee,
                                            newEmployee: Employee) = oldEmployee == newEmployee
        }
    }


    inner class EmployeeViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: Any?, viewModel: BaseViewModel) {
            obj.let {
                binding.setVariable(BR.obj, obj)
            }
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
        }
    }
}