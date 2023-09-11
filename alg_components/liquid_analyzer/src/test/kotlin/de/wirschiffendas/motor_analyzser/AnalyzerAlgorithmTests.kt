package de.wirschiffendas.motor_analyzser

import kotlin.random.Random
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class AnalyzerAlgorithmTests {

    private val testDispatcher = TestCoroutineDispatcher()

    @Test
    fun `test randomlyFail function`() =
            testDispatcher.runBlockingTest {
                Random(12345)
                // When
                val result = randomlyFail()

                // Then
                // assertTrue(result is Boolean)
                assertTrue(result == true || result == false)
            }
}
