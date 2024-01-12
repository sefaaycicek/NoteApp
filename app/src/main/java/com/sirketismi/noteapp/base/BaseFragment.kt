package com.sirketismi.noteapp.base

import android.content.Context
import android.hardware.input.InputManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    fun hideKeyboard(view : View?) {
        try {
            view?.let {
                val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputManager?.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } catch (ignored : Throwable) {

        }
    }
}