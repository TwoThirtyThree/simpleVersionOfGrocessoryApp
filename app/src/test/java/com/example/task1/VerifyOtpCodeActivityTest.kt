import android.content.Intent
import com.example.task1.ui.login.CredentialProvider
import com.example.task1.ui.login.VerifyOtpCodeActivity
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28, 32])
class VerifyOtpCodeActivityTest {

    @Mock
    private lateinit var mockFirebaseAuth: FirebaseAuth

    @Mock
    private lateinit var mockCredentialProvider: CredentialProvider

    @Mock
    private lateinit var mockIntent: Intent

    private lateinit var activity: VerifyOtpCodeActivity

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        activity = VerifyOtpCodeActivity()
        activity.firebaseAuth = mockFirebaseAuth
        activity.credentialProvider = mockCredentialProvider
        activity.intent = mockIntent
    }

    @Test
    fun `verify code with correct OTP`() {
        val enteredOtp = "123456"
        val credential = mock(PhoneAuthCredential::class.java)

        // Mock the CredentialProvider to return a PhoneAuthCredential when the OTP is correct
        `when`(
            mockCredentialProvider.createPhoneAuthCredential(

                (enteredOtp) // Only check OTP, ignore verification ID
            )
        ).thenReturn(credential)

        // Mock FirebaseAuth signInWithCredential to return a successful result
        `when`(mockFirebaseAuth.signInWithCredential(credential)).thenReturn(mock())

        activity.verifyCode(enteredOtp)

        // Verify that the verification ID is not set because it's not used in the test case
        assertEquals(null, activity.verifiyOtp)
    }
//    @Test
//    fun `verify code with invalid OTP`() {
//        val expectedVerificationId = "null"
//        val invalidOtp = "5577!@"
//        val credential = PhoneAuthProvider.getCredential(expectedVerificationId, invalidOtp)
//
//        // Mock the intent to return the expected verification ID
//        `when`(mockIntent.getStringExtra("verificationId")).thenReturn(expectedVerificationId)
//
//        // Mock the CredentialProvider to return a PhoneAuthCredential when the OTP is invalid
//        `when`(
//            mockCredentialProvider.createPhoneAuthCredential(
//
//                invalidOtp
//            )
//        ).thenReturn(credential)
//
//        // Mock FirebaseAuth signInWithCredential to return a failed result
//        val mockAuthResult: Task<AuthResult> = Tasks.forException(Exception("Invalid OTP"))
//        `when`(mockFirebaseAuth.signInWithCredential(credential)).thenReturn(mockAuthResult)
//
//        activity.verifyCode(invalidOtp)
//
//        // Verify that the verification ID is not set because the OTP was invalid
//        assertEquals(expectedVerificationId, activity.verifiyOtp)
//     }

    @Test
        fun `verify code with empty OTP`() {
            val emptyOtp = ""

            activity.verifyCode(emptyOtp)

            // Verify that signInWithCredential is not called when OTP is empty
            assertEquals(null, activity.verifiyOtp)
        }
    }

