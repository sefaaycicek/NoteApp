package com.sirketismi.noteapp.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.sirketismi.noteapp.R
import com.sirketismi.noteapp.databinding.FragmentLoginBinding
import com.sirketismi.noteapp.util.showMessage

class LoginFragment : Fragment() {
    lateinit var binding : FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)

        binding.loginButton.setOnClickListener {
            login()
        }

        binding.registerButton.setOnClickListener {
            openRegisterPage()
        }
        return binding.root
    }


    fun login() {
        val firebaseAuth = Firebase.auth
        firebaseAuth.signInWithEmailAndPassword(
            binding.usernameEditText.text.toString(),
            binding.passwordEditText.text.toString())
            .addOnSuccessListener {
                openApp()
            }.addOnFailureListener {
                showMessage(getString(R.string.error_login_title), getString(R.string.error_login_detail))
            }
    }

    fun openApp() {
        val action = LoginFragmentDirections.actionLoginFragmentToNotesFragment()
        findNavController().navigate(action)
    }

    fun openRegisterPage() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }
}