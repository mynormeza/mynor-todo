package com.mynormeza.sampletodo.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.mynormeza.sampletodo.R
import com.mynormeza.sampletodo.ui.addlist.SimpleListAdapter
import com.mynormeza.sampletodo.db.entity.TodoList
import com.mynormeza.sampletodo.utils.AuthenticationState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(), TodoListAdapter.ClickListener{
    @Inject lateinit var auth: FirebaseAuth
    private val viewModel: MainViewModel by viewModels()
    private lateinit var rvTodoLists: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.main_fragment, container, false)
        val btnFab: FloatingActionButton
        btnFab = root.findViewById(R.id.fab)

        rvTodoLists = root.findViewById(R.id.rv_todo_lists)
        rvTodoLists.layoutManager = LinearLayoutManager(context)

        btnFab.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAddListFragment()
            findNavController().navigate(action)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.listsOfTodos.observe(viewLifecycleOwner, Observer {listsOfTodos->
            val adapter = TodoListAdapter(listsOfTodos, this)
            rvTodoLists.adapter = adapter
        })

        viewModel.autState.observe(viewLifecycleOwner, { authenticationState ->
            when(authenticationState!!){
                AuthenticationState.UNAUTHENTICATED -> findNavController().navigate(R.id.loginFragment)
                AuthenticationState.INVALID_AUTHENTICATION -> Timber.e("Login unsuccessful")
                AuthenticationState.AUTHENTICATED -> {
                    //TODO: observe list
                }
            }
        })
    }

    override fun onClickItem(todoList: TodoList) {
        val action = MainFragmentDirections.actionMainFragmentToListDetailsFragment(listName = todoList.name, listId = todoList.id)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_out -> {
                auth.signOut()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }
}