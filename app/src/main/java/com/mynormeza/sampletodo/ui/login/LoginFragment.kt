package com.mynormeza.sampletodo.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.mynormeza.sampletodo.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    @Inject lateinit var auth: FirebaseAuth
    private lateinit var errorUsername: TextInputLayout
    private lateinit var errorPassword: TextInputLayout
    lateinit var etUsername: TextInputEditText
    lateinit var etPassword: TextInputEditText
    lateinit var btnSignIn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.login_fragment, container, false)
        errorUsername = root.findViewById(R.id.til_username)
        errorPassword = root.findViewById(R.id.til_password)
        etUsername = root.findViewById(R.id.et_username)
        etPassword = root.findViewById(R.id.et_password)
        btnSignIn = root.findViewById(R.id.btn_sign_in)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        validationInput()
        btnSignIn.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Timber.d( "signInWithEmail:success")
                        findNavController().navigate(R.id.mainFragment)
                    } else {
                        Timber.e("signInWithEmail:failure")
                        Toast.makeText(requireContext(), "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }

    private fun validationInput() {
        etUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                val validatePass = etPassword.text.toString().trim()
                val content = etUsername.text.toString().trim()
                errorUsername.error =
                    if (content.isNotEmpty()) null else getString(R.string.username_error_message)
                errorUsername.error =
                    if (content.matches(Regex(emailPattern))) null else getString(R.string.username_error_email)
                btnSignIn.isEnabled = (content.isNotEmpty() && content.matches(Regex(emailPattern))) && validatePass.length in 6..20
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                val validateName= etUsername.text.toString().trim()
                val content = etPassword.text.toString()
                errorPassword.error = if (content.length in 6..20) null else getString(R.string.password_error_message)
                btnSignIn.isEnabled = content.length in 6..20 && (validateName.isNotEmpty() && validateName.matches(Regex(emailPattern)))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

    }

}