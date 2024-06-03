package com.example.task1.ui.login

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class CredentialProvider {
    fun getCredential(verificationId: String, otp: String): PhoneAuthCredential {
        return PhoneAuthProvider.getCredential(verificationId, otp)
    }

    fun createPhoneAuthCredential(verificationId: String, code: String): AuthCredential {
        TODO("Not yet implemented")
    }
}

