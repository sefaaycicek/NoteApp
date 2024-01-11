package com.sirketismi.noteapp.feature.splash

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.sirketismi.noteapp.databinding.FragmentSplashBinding
import com.sirketismi.noteapp.util.MySharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import java.lang.annotation.Inherited
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    val viewModel: SplashViewModel by viewModels()
    //@Inject
    //lateinit var preferences: MySharedPreferences

    lateinit var binding : FragmentSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater)
        playLottie()
        return binding.root
    }

    private fun playLottie() {
        binding.animationView.repeatCount = 0
        binding.animationView.playAnimation()

        binding.animationView.addAnimatorListener(object:AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
            }

            override fun onAnimationEnd(p0: Animator) {
                checkAutoLogin()
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
            }
        })
    }

    fun checkAutoLogin() {
        if(viewModel.isIntroPlayed) {
            if(FirebaseAuth.getInstance().currentUser != null) {
                val action = SplashFragmentDirections.actionSplashFragmentToNotesFragment()
                findNavController().navigate(action)
            } else {
                val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        } else {
            val action = SplashFragmentDirections.actionSplashFragmentToIntroFragment()
            findNavController().navigate(action)

            viewModel.saveIntroPassed()
        }
    }
}