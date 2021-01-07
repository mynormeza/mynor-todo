package com.mynormeza.sampletodo.ui.listdetails

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mynormeza.sampletodo.R
import com.mynormeza.sampletodo.adapters.TodoTaskAdapter
import com.mynormeza.sampletodo.db.entity.TodoTask
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListDetailsFragment : Fragment(), TodoTaskAdapter.MarkAsDoneListener, TodoTaskAdapter.DeleteListener {

    private val viewModel: ListDetailsViewModel by viewModels()
    private lateinit var rvTodoLists: RecyclerView
    private val args by navArgs<ListDetailsFragmentArgs>()
    lateinit var adapter: TodoTaskAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.list_details_fragment, container, false)
        val btnFab: FloatingActionButton
        btnFab = root.findViewById(R.id.fab)

        rvTodoLists = root.findViewById(R.id.rv_task_list)
        rvTodoLists.layoutManager = LinearLayoutManager(context)
        btnFab.setOnClickListener {

            val taskEditText = EditText(requireContext())
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Agregar nueva tarea")
                .setMessage("Que quieres hacer ahora?")
                .setView(taskEditText)
                .setPositiveButton("Agreagar", DialogInterface.OnClickListener { _, _ ->
                    val task = taskEditText.text.toString().trim()
                    val listId = args.listId
                    viewModel.saveTask(listId, task)
                    taskEditText.setText("")
                })
                .setNegativeButton("Cancelar", null)
                .create()
            dialog.show()
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.title = args.listName

        val listId = args.listId
        viewModel.loadTasks(listId).observe(viewLifecycleOwner, { list ->
             adapter = TodoTaskAdapter(list, this, this)
            rvTodoLists.adapter = adapter
        })
    }

    override fun onMarkAsDonItem(todoTask: TodoTask) {
        todoTask.status = true
        viewModel.markAsDone(todoTask)
    }

    override fun onDeleteItem(todoTask: TodoTask) {
        viewModel.deleteTask(todoTask)
    }
}