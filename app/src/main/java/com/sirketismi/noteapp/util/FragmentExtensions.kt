package com.sirketismi.noteapp.util

import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.showDatePicker(onDateSelected : (Long)->Unit ) {
    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Select note date")
        .build()

    datePicker.addOnPositiveButtonClickListener {
        onDateSelected(it)
    }

    datePicker.addOnCancelListener {

    }

    datePicker.addOnDismissListener {

    }

    datePicker.show(childFragmentManager, "date_picker")
}

fun Fragment.showMessage(title :String, detail : String) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(title)
        .setMessage(detail)
        .setNeutralButton("Vazgeç") { dialog, which->

        }
        .setNegativeButton("Reddet") { dialog, which->

        }
        .setPositiveButton("Kabul")  { dialog, which->

        }
        .show()
}
