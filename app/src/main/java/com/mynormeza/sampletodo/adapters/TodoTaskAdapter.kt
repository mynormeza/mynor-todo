package com.mynormeza.sampletodo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mynormeza.sampletodo.R
import com.mynormeza.sampletodo.db.entity.TodoList
import com.mynormeza.sampletodo.db.entity.TodoTask

class TodoTaskAdapter (
    private val todoLists: List<TodoTask>,
    private val deleteListener: DeleteListener,
    private val markAsDoneListener: MarkAsDoneListener,
) : RecyclerView.Adapter<TodoTaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.todo_task_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(todoLists[position], deleteListener, markAsDoneListener)
    }

    override fun getItemCount(): Int {
        return todoLists.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(todoTask: TodoTask,
                      deleteListener: DeleteListener,
                      markAsDoneListener: MarkAsDoneListener) {
            val taskName = itemView.findViewById(R.id.tv_task_name) as TextView
            val deleteButton = itemView.findViewById(R.id.ib_delete) as ImageButton
            val doneButton = itemView.findViewById(R.id.ib_done) as ImageButton
            val doneImage = itemView.findViewById(R.id.iv_done) as ImageView
            taskName.text = todoTask.name
            if (todoTask.status) {
                doneImage.visibility = View.VISIBLE
                doneButton.visibility = View.GONE
            }

            deleteButton.setOnClickListener {
                deleteListener.onDeleteItem(todoTask)
            }
            doneButton.setOnClickListener {
                markAsDoneListener.onMarkAsDonItem(todoTask)
            }
        }
    }

    interface DeleteListener {
        fun onDeleteItem(todoTask: TodoTask)
    }

    interface MarkAsDoneListener {
        fun onMarkAsDonItem(todoTask: TodoTask)
    }



}