package com.example.task1.ui.login

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import com.example.task1.MyDashBoard.TestApplication
import com.example.task1.R
import com.example.task1.ui.dashboard.DashboardActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], application = TestApplication::class) // Use appropriate SDK version
class VerifyOtpCodeActivityTest {

    @Mock
    private lateinit var mockFirebaseAuth: FirebaseAuth

    @Mock
    private lateinit var mockTask: Task<AuthResult>

    @Captor
    private lateinit var credentialCaptor: ArgumentCaptor<AuthCredential>

    private lateinit var verifyOtpCodeActivity: VerifyOtpCodeActivity

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        verifyOtpCodeActivity = Robolectric.buildActivity(VerifyOtpCodeActivity::class.java).create().get()
        verifyOtpCodeActivity.firebaseAuth = mockFirebaseAuth
    }

    @Test
    fun testValidOtpVerification() {
        // Setup the activity with a mock verification ID
        verifyOtpCodeActivity.verifiyOtp = "testVerificationId"

        // Set up the views
        val editTextOtp = verifyOtpCodeActivity.findViewById<EditText>(R.id.edittext_otp_verify)
         val verifyOtpButton = verifyOtpCodeActivity.findViewById<Button>(R.id.button_otp_login)

        // Set the test input
        editTextOtp.setText("123456")

        // Mock the signInWithCredential method
        `when`(mockFirebaseAuth.signInWithCredential(any(AuthCredential::class.java))).thenReturn(mockTask)
        `when`(mockTask.addOnCompleteListener(any())).thenAnswer {
            (it.arguments[0] as OnCompleteListener<AuthResult>).onComplete(mockTask)
            mockTask
        }

        // Simulate a successful sign-in task
        `when`(mockTask.isSuccessful).thenReturn(true)

//        // Simulate the verify button click
        verifyOtpButton.performClick()

        // Verify that signInWithCredential was called
        verify(mockFirebaseAuth).signInWithCredential(any(AuthCredential::class.java))

        // Capture the next started activity
        val nextStartedActivity = shadowOf(verifyOtpCodeActivity).nextStartedActivity
        assertNotNull(nextStartedActivity)
        assertEquals(DashboardActivity::class.java.name, nextStartedActivity.component?.className)
    }


    @Test
    fun testInvalidOtpVerification() {
        // Setup the activity with a mock verification ID
        verifyOtpCodeActivity.verifiyOtp = "testVerificationId"

        // Set up the views
        val editTextOtp = verifyOtpCodeActivity.findViewById<EditText>(R.id.edittext_otp_verify)
        val verifyOtpButton = verifyOtpCodeActivity.findViewById<Button>(R.id.button_otp_login)

        // Set the test input
        editTextOtp.setText("invalidOtp")

        // Mock the signInWithCredential method
        `when`(mockFirebaseAuth.signInWithCredential(any(AuthCredential::class.java))).thenReturn(mockTask)
        `when`(mockTask.addOnCompleteListener(any())).thenAnswer {
            (it.arguments[0] as OnCompleteListener<AuthResult>).onComplete(mockTask)
            mockTask
        }

        // Simulate a failed sign-in task
        `when`(mockTask.isSuccessful).thenReturn(false)
        `when`(mockTask.exception).thenReturn(FirebaseAuthInvalidCredentialsException("ERROR", "Invalid OTP"))

        // Simulate the verify button click
        verifyOtpButton.performClick()

        // Verify that signInWithCredential was called
        verify(mockFirebaseAuth).signInWithCredential(any(AuthCredential::class.java))

        // Verify that the error message is shown on failed sign-in
        val toastMessage = ShadowToast.getTextOfLatestToast()
        assertEquals("Invalid OTP", toastMessage)
    }
}
