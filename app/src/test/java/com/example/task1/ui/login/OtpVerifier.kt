
package com.example.task1.ui.login


import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class OtpVerifier(private val firebaseAuth: FirebaseAuth) {

    fun verifyCode(verificationId: String?, code: String, callback: (Boolean, String?) -> Unit) {
        if (verificationId.isNullOrEmpty()) {
            callback(false, "Verification ID is null or empty")
            return
        }

        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential, callback)
    }

    private fun signInWithCredential(credential: AuthCredential, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    val errorMessage = task.exception?.message ?: "Unknown error"
                    callback(false, errorMessage)
                }
            }
    }
}
