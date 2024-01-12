package com.sirketismi.noteapp.feature.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.sirketismi.noteapp.R
import com.sirketismi.noteapp.databinding.FragmentLoginBinding
import com.sirketismi.noteapp.util.showMessage
import java.util.Arrays

class LoginFragment : Fragment() {
    lateinit var binding : FragmentLoginBinding

    private var callbackManager: CallbackManager = CallbackManager.Factory.create()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)

        binding.loginButton.setOnClickListener {
           // login()
            loginFacebook()
        }

        binding.registerButton.setOnClickListener {
            //openRegisterPage()
            loginGoogle()
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

    private lateinit var signInRequest: BeginSignInRequest

    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private lateinit var mGoogleSignInClient : GoogleSignInClient
    fun loginGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(getString(R.string.google_client_id))
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail().build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.google_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .build()

        val signInIntent = mGoogleSignInClient.signInIntent
        googleLauncher.launch(signInIntent)
    }

    private val googleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: androidx.activity.result.ActivityResult ->

        result.data?.let {
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(it)
            if(result?.isSuccess == true) {
                val acct = result.signInAccount
                val authCode = acct!!.serverAuthCode
                val idToken = acct!!.idToken

                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                Firebase.auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success")
                            val user = auth.currentUser
                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.exception)
                            //updateUI(null)
                        }
                    }
            }
        }

    }


    fun loginFacebook() {
        //binding.loginButton.setReadPermissions(arrayListOf("email", "public_profile"))
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    handleFacebookAccessToken(loginResult.accessToken)
                }

                override fun onCancel() {
                    Log.d("TAG", "facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    Log.d("TAG", "facebook:onError", error)
                }
            },
        )

        LoginManager.getInstance().logInWithReadPermissions(this, callbackManager, PERMISSIONS)
    }

    val PERMISSIONS = Arrays.asList("public_profile", "email")

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

           // callbackManager.onActivityResult(requestCode, resultCode, data)

        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("TAG", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    //updateUI(null)
                }
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