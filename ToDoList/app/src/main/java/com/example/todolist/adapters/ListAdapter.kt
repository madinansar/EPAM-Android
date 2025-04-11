package com.example.todolist

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ListItemBinding

class ListAdapter(
    private val lists: List<TheList>,
    private val onItemClick: (TheList) -> Unit
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(list: TheList) {
            binding.etTaskName.text = list.name

            binding.etTaskName.setOnClickListener {
                val context = it.context
                val intent = Intent(context, EditListActivity::class.java).apply {
                    putExtra("list_data", list) // Passing the whole list
                }
                context.startActivity(intent)
            }


            binding.btnEditTask.setOnClickListener {
                onItemClick(list)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(lists[position])
    }

    override fun getItemCount() = lists.size
}
