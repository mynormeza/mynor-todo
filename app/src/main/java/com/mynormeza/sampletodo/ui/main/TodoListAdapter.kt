package com.mynormeza.sampletodo.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mynormeza.sampletodo.R
import com.mynormeza.sampletodo.db.entity.TodoList

class TodoListAdapter (
    private val todoLists: List<TodoList>,
    private val clickListener: ClickListener
) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    interface ClickListener {
        fun onClickItem(todoList: TodoList)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.simple_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(todoLists[position], clickListener)
    }

    override fun getItemCount(): Int {
        return todoLists.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(orderList: TodoList, clickListener: ClickListener) {
            itemView.setOnClickListener {
                clickListener.onClickItem(orderList)
            }
            val listName = itemView.findViewById(R.id.tv_list_name) as TextView
            listName.text = orderList.name
        }
    }


}