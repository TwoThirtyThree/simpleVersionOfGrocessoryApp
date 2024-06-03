package com.example.task1.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.task1.R
import com.example.task1.ui.dashboard.DashboardActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class VerifyOtpCodeActivity : AppCompatActivity() {
    lateinit var editTextOtp: EditText
    lateinit var verifyOtpButton: Button
    var firebaseAuth: FirebaseAuth? = null
    var verifiyOtp: String? = null
    var credentialProvider: CredentialProvider = CredentialProvider() // Injected for testing
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_verify_otp_code)
        initViews()
        intentRecieverOtp()
        verifyCodelistener()
    }


    private fun initViews() {
        firebaseAuth = FirebaseAuth.getInstance()
        editTextOtp = findViewById(R.id.edittext_otp_verify)
        verifyOtpButton = findViewById(R.id.button_otp_login)
    }

    fun intentRecieverOtp() {
        // verifiyOtp = intent.getStringExtra("verificationId")

        verifiyOtp = intent.getStringExtra("verificationId") ?: ""


    }

    fun verifyCodelistener() {
        verifyOtpButton.setOnClickListener(View.OnClickListener {

            if (TextUtils.isEmpty(editTextOtp.getText().toString())) {

                Toast.makeText(this@VerifyOtpCodeActivity, "Please enter OTP", Toast.LENGTH_SHORT)
                    .show()
            } else {
                verifyCode(editTextOtp.getText().toString())
            }
        })
    }

        fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verifiyOtp!!, code)
        signInWithCredential(credential)
    }
//    fun verifyCode(code: String) {
//        val verificationId = intent.getStringExtra("verificationId") ?: return
//
//        if (code.isNotEmpty()) {
//            val credential = credentialProvider.createPhoneAuthCredential(verificationId, code)
//            firebaseAuth?.signInWithCredential(credential)
//            verifiyOtp = verificationId
//            signInWithCredential(credential)
//        }
//}

        fun signInWithCredential(credential: PhoneAuthCredential) {

            firebaseAuth!!.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val i = Intent(
                            this@VerifyOtpCodeActivity,
                            DashboardActivity::class.java
                        )
                        startActivity(i)
                        finish()
                    } else {

                        Toast.makeText(
                            this@VerifyOtpCodeActivity,
                            task.exception!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        fun createPhoneAuthCredential(verificationId: String, otp: String): PhoneAuthCredential {
            return PhoneAuthProvider.getCredential(verificationId, otp)
        }
    }
