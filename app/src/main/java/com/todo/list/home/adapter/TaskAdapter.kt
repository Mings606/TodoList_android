package com.todo.list.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.todo.list.R
import com.todo.list.data.TaskTable
import com.todo.list.databinding.ItemTaskBinding

class TaskAdapter(
    private val context: Context,
    private val mValues: List<TaskTable>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: TaskTable, position: Int)
        fun onItemCheckedClick(item: TaskTable, position: Int)
        fun onItemEditClick(item: TaskTable, position: Int)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        val binding = holder.mBinding
        val item = mValues[position]

        binding.tvTaskName.text = item.taskName
        binding.taskCheckbox.isChecked = item.isCompleted

        binding.btnEdit.isVisible = !item.isCompleted

        binding.btnDelete.setOnClickListener {
            buttonOneClick(it)
            listener.onItemClick(item, position)
        }

        binding.btnEdit.setOnClickListener {
            buttonOneClick(it)
            listener.onItemEditClick(item, position)
        }

        binding.taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
            item.isCompleted = isChecked
            binding.btnEdit.isVisible = !isChecked
            listener.onItemCheckedClick(item, position)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder {

        val layoutInflater =
            if (LayoutInflater.from(parent.context) == null) LayoutInflater.from(parent.context)
            else LayoutInflater.from(parent.context)

        val binding: ItemTaskBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_task, parent, false)

        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        var mBinding = binding
    }

    /**  Button Single Click */
    private fun buttonOneClick(v: View) {
        v.isClickable = false
        v.postDelayed({ v.isClickable = true }, 800)
    }
}
