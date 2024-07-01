package com.example.task1.ui.login

import com.example.task1.util.PhoneAuthProviderWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [24, 32], application = TestApplication::class, manifest = Config.NONE)
class SendVerificationCodeTest {

    private lateinit var loginActivity: LoginActivity
    private lateinit var mockFirebaseAuth: FirebaseAuth
    private lateinit var mockPhoneNumberUtil: PhoneNumberUtil
    private lateinit var mockCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var mockPhoneAuthProviderWrapper: PhoneAuthProviderWrapper

    @Before
    fun setUp() {
        // initialize the activity using Robolectric
        loginActivity = Robolectric.buildActivity(LoginActivity::class.java).create().start().resume().get()
        // Initialize the mocks
        mockFirebaseAuth = mock()
        mockPhoneNumberUtil = mock()
        mockCallbacks = mock()
        mockPhoneAuthProviderWrapper = mock()
        // Inject the mocks into the activity
        loginActivity.phoneAuthProviderWrapper = mockPhoneAuthProviderWrapper
    }

    private fun setUpPhoneNumberUtil(phoneNumber: String, isValid: Boolean) {
        val numberProto = Phonenumber.PhoneNumber()
        whenever(mockPhoneNumberUtil.parse(phoneNumber, null)).thenReturn(numberProto)
        whenever(mockPhoneNumberUtil.isValidNumber(numberProto)).thenReturn(isValid)
        whenever(mockPhoneNumberUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.E164)).thenReturn(phoneNumber)
    }

    @Test
    fun testSendVerificationCodeValidIndianNumber() {
        val phoneNumber = "+919876543210" // Valid Indian number
        setUpPhoneNumberUtil(phoneNumber, true)
        loginActivity.sendVerificationCode(phoneNumber)
        verify(mockPhoneAuthProviderWrapper, times(1)).verifyPhoneNumber(any())
    }

    @Test
    fun testSendVerificationCodeInvalidIndianNumber() {
        val phoneNumber = "+91987654321" // Invalid Indian number
        setUpPhoneNumberUtil(phoneNumber, false)
        loginActivity.sendVerificationCode(phoneNumber)
        verify(mockPhoneAuthProviderWrapper, times(1)).verifyPhoneNumber(any())
    }

    @Test
    fun testSendVerificationCodeValidPakistaniNumber() {
        val phoneNumber = "+923001234567" // Valid Pakistan number
        setUpPhoneNumberUtil(phoneNumber, true)
        loginActivity.sendVerificationCode(phoneNumber)
        verify(mockPhoneAuthProviderWrapper, times(1)).verifyPhoneNumber(any())
    }

    @Test
    fun testSendVerificationCodeInvalidPakistaniNumber() {
        val phoneNumber = "+92300123456" // Invalid Pakistan number
        setUpPhoneNumberUtil(phoneNumber, false)
        loginActivity.sendVerificationCode(phoneNumber)
        verify(mockPhoneAuthProviderWrapper, times(1)).verifyPhoneNumber(any())
    }

    @Test
    fun testSendVerificationCodeValidUKNumber() {
        val phoneNumber = "+447912345678" // Valid UK number
        setUpPhoneNumberUtil(phoneNumber, true)
        loginActivity.sendVerificationCode(phoneNumber)
        verify(mockPhoneAuthProviderWrapper, times(1)).verifyPhoneNumber(any())
    }

    @Test
    fun testSendVerificationCodeInvalidUKNumber() {
        val phoneNumber = "+44791234567" // Invalid UK number
        setUpPhoneNumberUtil(phoneNumber, false)
        loginActivity.sendVerificationCode(phoneNumber)
        verify(mockPhoneAuthProviderWrapper, times(1)).verifyPhoneNumber(any())
    }
}
