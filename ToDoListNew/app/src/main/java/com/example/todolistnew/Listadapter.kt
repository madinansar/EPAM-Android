package com.example.todolistnew

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(
    private val context: Context,
    private val lists: List<TheList>,
    private val dbHelper: DatabaseHelper
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvListName: TextView = itemView.findViewById(R.id.et_task_name)
        val btnMenu: Button = itemView.findViewById(R.id.btnMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val list = lists[position]
        holder.tvListName.text = list.name

        holder.tvListName.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EditListActivity::class.java)
            intent.putExtra("list_name", list.name)

            DataHolder.selectedList = list
            context.startActivity(intent)
        }

        holder.btnMenu.setOnClickListener { view ->
            val popupMenu = PopupMenu(context, view)
            val menuInflater = popupMenu.menuInflater
            menuInflater.inflate(R.menu.listmenu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.list_edit -> {
                        showUpdateListDialog(list)
                        true
                    }
                    R.id.list_delete -> {
                        dbHelper.deleteList(list.id)
                        val pos = lists.indexOf(list)
                        (lists as MutableList).removeAt(pos)
                        notifyItemRemoved(pos)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    override fun getItemCount() = lists.size
    private fun showUpdateListDialog(list: TheList) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Update List")

        val edittext = EditText(context)
        edittext.hint = "Enter list name"
        edittext.setText(list.name) // Prefill the current list name
        edittext.setPadding(40, 20, 40, 20)

        dialog.setView(edittext)

        dialog.setPositiveButton("Update") { d, _ ->
            val updatedListName = edittext.text.toString().trim()
            if (updatedListName.isNotEmpty()) {
                list.name = updatedListName
                dbHelper.updateList(list.id, updatedListName)
                notifyItemChanged(lists.indexOf(list))
            }
            d.dismiss()
        }

        dialog.setNegativeButton("Cancel") { d, _ ->
            d.cancel()
        }

        dialog.create().show()
    }
}
