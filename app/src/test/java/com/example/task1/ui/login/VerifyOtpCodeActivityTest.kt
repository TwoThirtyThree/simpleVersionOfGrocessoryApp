package com.example.task1.logic

import com.example.task1.ui.login.OtpVerifier
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*

class VerifyOtpCodeActivityTest {

    @Mock
    private lateinit var mockFirebaseAuth: FirebaseAuth

    @Mock
    private lateinit var mockTask: Task<AuthResult>

    private lateinit var otpVerifier: OtpVerifier

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        otpVerifier = OtpVerifier(mockFirebaseAuth)
    }

    @Test
    fun testValidOtpVerification() {
        // Mocking verification ID and OTP
        val verificationId = "testVerificationId"
        val otpCode = "123456"
        // Mock the signInWithCredential method
        `when`(mockFirebaseAuth.signInWithCredential(any(AuthCredential::class.java))).thenReturn(mockTask)
        `when`(mockTask.addOnCompleteListener(any())).thenAnswer {
            (it.arguments[0] as OnCompleteListener<AuthResult>).onComplete(mockTask)
            mockTask
        }
        // Simulate a successful sign-in task
        `when`(mockTask.isSuccessful).thenReturn(true)
        // Call the verifyCode method directly
        otpVerifier.verifyCode(verificationId, otpCode) { success, message ->
            assertTrue(success)
            assertNull(message)
        }
        // Verify that signInWithCredential was called
        verify(mockFirebaseAuth).signInWithCredential(any(AuthCredential::class.java))
    }

    @Test
    fun testInvalidOtpVerification() {
        // Mocking verification ID and OTP
        val verificationId = "testVerificationId"
        val otpCode = "invalidOtp"
        // Mock the signInWithCredential method
        `when`(mockFirebaseAuth.signInWithCredential(any(AuthCredential::class.java))).thenReturn(mockTask)
        `when`(mockTask.addOnCompleteListener(any())).thenAnswer {
            (it.arguments[0] as OnCompleteListener<AuthResult>).onComplete(mockTask)
            mockTask
        }

        // Simulate a failed sign-in task
        `when`(mockTask.isSuccessful).thenReturn(false)
        `when`(mockTask.exception).thenReturn(FirebaseAuthInvalidCredentialsException("ERROR", "Invalid OTP"))

        // Call the verifyCode method directly
        otpVerifier.verifyCode(verificationId, otpCode) { success, message ->
            assertFalse(success)
            assertEquals("Invalid OTP", message)
        }

        // Verify that signInWithCredential was called
        verify(mockFirebaseAuth).signInWithCredential(any(AuthCredential::class.java))
    }
}
