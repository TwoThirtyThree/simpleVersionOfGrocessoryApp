package com.example.task1.ui.login

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test

class PhoneNumberTest {

    private lateinit var phoneNumberUtil: PhoneNumberUtil

    @Before
    fun setUp() {
        phoneNumberUtil = PhoneNumberUtil.getInstance()
    }

    // Valid Indian Phone Number
    @Test
    fun testValidIndianPhoneNumber() {
        val phoneNumber = "+919876543210"
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNotNull(formattedNumber)
        assertEquals("+919876543210", formattedNumber)
    }

    // Invalid Indian Phone Numbers Short
    @Test
    fun testInvalidIndianPhoneNumberShort() {
        val phoneNumber = "+91987654321" // 9 digits instead of 10
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNull(formattedNumber)
    }
    // Invalid Indian Phone Numbers Long
    @Test
    fun testInvalidIndianPhoneNumberLong() {
        val phoneNumber = "+9198765432100"
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNull(formattedNumber)
    }
    // Invalid Indian Phone Numbers Non Numeric
    @Test
    fun testInvalidIndianPhoneNumberNonNumeric() {
        val phoneNumber = "+91abcdefghij"
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNull(formattedNumber)
    }

    // Valid Pakistan Phone Number
    @Test
    fun testValidPakistanPhoneNumber() {
        val phoneNumber = "+923001234567"
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNotNull(formattedNumber)
        assertEquals("+923001234567", formattedNumber)
    }

    // Invalid Pakistan Phone Numbers Short
    @Test
    fun testInvalidPakistanPhoneNumberShort() {
        val phoneNumber = "+92300123456" // 9 digits instead of 10
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNull(formattedNumber)
    }
    // Invalid Pakistan Phone Numbers Long
    @Test
    fun testInvalidPakistanPhoneNumberLong() {
        val phoneNumber = "+9230012345678" // 11 digits instead of 10
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNull(formattedNumber)
    }
    // Invalid Pakistan Phone Numbers Non Numeric
    @Test
    fun testInvalidPakistanPhoneNumberNonNumeric() {
        val phoneNumber = "+92abcdefghij"
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNull(formattedNumber)
    }

    // Valid UK Phone Number
    @Test
    fun testValidUKPhoneNumber() {
        val phoneNumber = "+447911123456"
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNotNull(formattedNumber)
        assertEquals("+447911123456", formattedNumber)
    }

    // Invalid UK Phone Numbers Short
    @Test
    fun testInvalidUKPhoneNumberShort() {
        val phoneNumber = "+44791112345" // 9 digits instead of 10
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNull(formattedNumber)
    }
    // Invalid UK Phone Numbers Long
    @Test
    fun testInvalidUKPhoneNumberLong() {
        val phoneNumber = "+4479111234567" // 11 digits instead of 10
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNull(formattedNumber)
    }
    // Invalid UK Phone Numbers Non Numeric
    @Test
    fun testInvalidUKPhoneNumberNonNumeric() {
        val phoneNumber = "+44abcdefghij"
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNull(formattedNumber)
    }

    // Valid Dubai Phone Number
    @Test
    fun testValidDubaiPhoneNumber() {
        val phoneNumber = "+971501234567"
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNotNull(formattedNumber)
        assertEquals("+971501234567", formattedNumber)
    }

    // Invalid Dubai Phone Numbers Short
    @Test
    fun testInvalidDubaiPhoneNumberShort() {
        val phoneNumber = "+97150123456" // 8 digits instead of 9
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNull(formattedNumber)
    }
    // Invalid Dubai Phone Numbers Long
    @Test
    fun testInvalidDubaiPhoneNumberLong() {
        val phoneNumber = "+9715012345678" // 10 digits instead of 9
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNull(formattedNumber)
    }
    // Invalid Dubai Phone Numbers Non Numeric
    @Test
    fun testInvalidDubaiPhoneNumberNonNumeric() {
        val phoneNumber = "+971abcdefghi"
        val formattedNumber = formatPhoneNumber(phoneNumber)
        assertNull(formattedNumber)
    }

    private fun formatPhoneNumber(phoneNumber: String): String? {
        return try {
            val numberProto = phoneNumberUtil.parse(phoneNumber, null)
            if (phoneNumberUtil.isValidNumber(numberProto)) {
                phoneNumberUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.E164)
            } else {
                null
            }
        } catch (e: NumberParseException) {
            null
        }
    }
}
