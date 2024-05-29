package com.example.task1.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils

import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task1.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth? = null
    private var verificationId: String? = null
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var editTextCountryCode: EditText

    private lateinit var otpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        otpClickListener()
    }

    private fun initViews() {
        firebaseAuth = FirebaseAuth.getInstance()
        editTextPhoneNumber = findViewById(R.id.edittext_phone_number)
        editTextCountryCode = findViewById(R.id.edittext_country_code)
        otpButton = findViewById(R.id.button_get_otp)
    }



    private fun otpClickListener() {
        otpButton.setOnClickListener {
            val countryCode = editTextCountryCode.text.toString().trim()
            val phoneNumber = editTextPhoneNumber.text.toString().trim()

            if (TextUtils.isEmpty(countryCode)) {
                Toast.makeText(this@LoginActivity, "Please enter a country code.", Toast.LENGTH_SHORT).show()
            } else if (TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(this@LoginActivity, "Please enter a phone number.", Toast.LENGTH_SHORT).show()
            } else {
                val fullPhoneNumber = "+$countryCode$phoneNumber"
                val formattedPhoneNumber = formatPhoneNumber(fullPhoneNumber)
                if (formattedPhoneNumber != null) {
                    sendVerificationCode(formattedPhoneNumber)

                } else {
                    Toast.makeText(this@LoginActivity, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    private fun formatPhoneNumber(phoneNumber: String): String? {
        val phoneUtil = PhoneNumberUtil.getInstance()
        return try {
            val numberProto: Phonenumber.PhoneNumber = phoneUtil.parse(phoneNumber, null)
            if (phoneUtil.isValidNumber(numberProto)) {
                phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.E164)
            } else {
                null
            }
        } catch (e: NumberParseException) {
            null
        }
    }



    private fun sendVerificationCode(number: String) {
        firebaseAuth?.let {
            val options = PhoneAuthOptions.newBuilder(it)
                .setPhoneNumber(number)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallBack)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                verificationId = s
                val i = Intent(this@LoginActivity, VerifyOtpCodeActivity::class.java)
                i.putExtra("verificationId", verificationId)
                startActivity(i)
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {}

            override fun onVerificationFailed(e: FirebaseException) {

            }
        }
}