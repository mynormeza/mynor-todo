package com.mynormeza.sampletodo.ui.addlist

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.mynormeza.sampletodo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddListFragment : Fragment() {

    private val viewModel: AddListViewModel by viewModels()
    private lateinit var rvTasks: RecyclerView
    private lateinit var btnFab: FloatingActionButton
    lateinit var etListName: TextInputEditText
    lateinit var pbLoading: ProgressBar
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.add_list_fragment, container, false)

        btnFab = root.findViewById(R.id.fab)
        etListName = root.findViewById(R.id.et_list_name)
        rvTasks = root.findViewById(R.id.rv_todo_tasks)
        rvTasks.layoutManager = LinearLayoutManager(context)
        pbLoading = root.findViewById(R.id.loading)
        val taskEditText = EditText(requireContext())
        dialog = AlertDialog.Builder(requireContext())
            .setTitle("Agregar nueva tarea")
            .setMessage("Que quieres hacer ahora?")
            .setView(taskEditText)
            .setPositiveButton("Agreagar", DialogInterface.OnClickListener { dialog, which ->
                val task = taskEditText.text.toString().trim()
                viewModel.saveTask(task)
                taskEditText.setText("")
            })
            .setNegativeButton("Cancelar", null)
            .create()

        btnFab.setOnClickListener {
            val listName = etListName.text.toString().trim()
            if (listName.isNotEmpty()) {
                if (viewModel.listId == -1L) {
                    viewModel.saveList(listName)
                } else {
                    dialog.show()
                }
            } else {
                Toast.makeText(requireContext(), "Por favor agregue nombre de lista primero", Toast.LENGTH_SHORT).show()
            }

        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                pbLoading.visibility = View.VISIBLE
            }else {
                pbLoading.visibility = View.GONE
                rvTasks.visibility = View.VISIBLE
                dialog.show()
            }
        })

        viewModel.listsOfTasks.observe(viewLifecycleOwner, { listsOfTodos ->
            val adapter = SimpleListAdapter(listsOfTodos)
            rvTasks.adapter = adapter
        })


    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_list_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_end -> {
                if(viewModel.listId == -1L) {
                    Toast.makeText(requireContext(), "No se guardo niguna tarea o lista", Toast.LENGTH_SHORT).show()
                }
                findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)

    }

}