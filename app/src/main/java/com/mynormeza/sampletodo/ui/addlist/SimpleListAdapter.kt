package com.mynormeza.sampletodo.ui.addlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mynormeza.sampletodo.R
import com.mynormeza.sampletodo.db.entity.TodoList
import com.mynormeza.sampletodo.db.entity.TodoTask

class SimpleListAdapter (
    private val todoLists: List<TodoTask>,
) : RecyclerView.Adapter<SimpleListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.simple_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(todoLists[position])
    }

    override fun getItemCount(): Int {
        return todoLists.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(orderList: TodoTask) {
            val listName = itemView.findViewById(R.id.tv_list_name) as TextView
            listName.text = orderList.name
        }
    }


}