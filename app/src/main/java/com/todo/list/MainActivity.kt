package com.todo.list

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.todo.list.data.TaskTable
import com.todo.list.databinding.ActivityMainBinding
import com.todo.list.home.adapter.TaskAdapter
import com.todo.list.home.vm.TaskViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        bindingView()
        dbGetAllHistory()
    }

    private fun bindingView() {

        binding.btnAddTask.setOnClickListener {
            val taskName = binding.etTaskInput.text.toString()
            if (taskName.isNotEmpty()) {
                val task = TaskTable(0, taskName)
                taskViewModel.insert(task)
                binding.etTaskInput.text.clear()
            }

            dbGetAllHistory()
        }

    }

    private fun dbGetAllHistory() {
        taskViewModel.readAllData.observe(this) {
            binding.rvList.adapter = null
            adapterPairing(it)
        }
    }

    private fun adapterPairing(list: List<TaskTable>) {
        runOnUiThread {
            if (binding.rvList.adapter == null) {
                binding.rvList.adapter =
                    TaskAdapter(this, list,
                        object : TaskAdapter.OnItemClickListener {
                            override fun onItemClick(item: TaskTable, position: Int) {
                                taskViewModel.delete(item)
                                dbGetAllHistory()
                            }

                            override fun onItemEditClick(item: TaskTable, position: Int) {
                                binding.etTaskInput.setText(item.taskName)
                                binding.etTaskInput.setSelection(binding.etTaskInput.text.length)
                                taskViewModel.delete(item)
                                dbGetAllHistory()
                            }

                            override fun onItemCheckedClick(item: TaskTable, position: Int) {
                                taskViewModel.update(item)
                                dbGetAllHistory()
                            }
                        })
            } else {
                Handler().postDelayed({
                    binding.rvList.adapter?.notifyDataSetChanged()
                }, 100)
            }
        }
    }
}
