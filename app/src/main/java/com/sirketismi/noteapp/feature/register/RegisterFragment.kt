package com.sirketismi.noteapp.feature.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.auth
import com.sirketismi.noteapp.R
import com.sirketismi.noteapp.databinding.FragmentRegisterBinding
import com.sirketismi.noteapp.util.FirebaseMessageHandler
import com.sirketismi.noteapp.util.showMessage

class RegisterFragment : Fragment() {
    lateinit var binding : FragmentRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)

        binding.registerButton.setOnClickListener {
            register()
        }
        return binding.root
    }


    fun register() {
        val firebaseAuth = Firebase.auth
        firebaseAuth.createUserWithEmailAndPassword(
            binding.emailEditText.text.toString(),
            binding.registerPasswordEditText.text.toString())
            .addOnSuccessListener {
                findNavController().popBackStack()
        }.addOnFailureListener {
            (it as? FirebaseAuthException)?.errorCode?.let { errorCode->
                showMessage("hata", FirebaseMessageHandler.getLocalizedMessage(errorCode))
            }

        }
    }
}