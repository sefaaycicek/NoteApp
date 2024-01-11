package com.sirketismi.noteapp.util

class FirebaseMessageHandler {
    companion object {
        fun getLocalizedMessage(errorCode : String) : String {
            return when(errorCode) {
                "ERROR_WEAK_PASSWORD" -> "6 haneli bir şifre belirleyin"
                "ERROR_INVALID_EMAIL" -> "Geçerli bir email giriniz."
                else-> "Giriş bilgilerini kontrol ediniz."
             }
        }
    }
}