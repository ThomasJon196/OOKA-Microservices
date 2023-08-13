package com.example.kotlin_gradle_threading

import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SuspendFunctionTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @Test
    fun `test suspendFunction`() = testDispatcher.runBlockingTest {
        val result = suspendFunction()
        
        assertEquals("Suspended function completed", result)
    }
}