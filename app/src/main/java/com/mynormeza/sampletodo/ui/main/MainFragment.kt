package com.mynormeza.sampletodo.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.mynormeza.sampletodo.R
import com.mynormeza.sampletodo.utils.AuthenticationState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    @Inject lateinit var auth: FirebaseAuth
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

}