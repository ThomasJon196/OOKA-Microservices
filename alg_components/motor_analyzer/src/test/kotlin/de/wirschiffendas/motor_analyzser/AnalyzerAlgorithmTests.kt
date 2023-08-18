package de.wirschiffendas.motor_analyzser

import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AnalyzerAlgorithmTests {

    private val testDispatcher = TestCoroutineDispatcher()

    @Test
    fun `test suspendFunction`() =
            testDispatcher.runBlockingTest {
                val result = suspendFunction()

                assertEquals("Suspended function completed", result)
            }

    @Test
    fun `test algorithms return correct return value types`() =
            testDispatcher.runBlockingTest {
                // When
                val gearAnalyzer = GearboxAnalyzer()
                gearAnalyzer.executeAnalysis()

                val engineAnalyzer = EngineAnalyzer()
                engineAnalyzer.executeAnalysis()

                val startingSystemAnalyzer = StartingSystemAnalyzer()
                startingSystemAnalyzer.executeAnalysis()

                // val resultEngine = executeEngineAnalysis()
                // val resultStartingSystem = executeStartingSystemAnalysis()
                
                // Then
                assert(gearAnalyzer.status in Status.values())
                assert(engineAnalyzer.status in Status.values())
                assert(startingSystemAnalyzer.status in Status.values())
            }

    @Test
    fun `test motor analyzer returns correct return value type`() =
            testDispatcher.runBlockingTest {
                // When
                val motorAnalyzer = MotorAnalyzer()
                motorAnalyzer.executeAnalysis()

                // Then
                assert(motorAnalyzer.status in Status.values())
            }
}

