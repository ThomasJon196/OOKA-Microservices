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
                // Assert response is inside Status Enum
                // assert(resultGearbox in Status.values())
                // assert(resultEngine in Status.values())
                // assert(resultStartingSystem in Status.values())
                // assertEquals(Status.OK, gearAnalyzer.status)
                // assertEquals(Status.OK, engineAnalyzer.status)
                // assertEquals(Status.OK, startingSystemAnalyzer.status)
                assert(gearAnalyzer.status in Status.values())
                assert(engineAnalyzer.status in Status.values())
                assert(startingSystemAnalyzer.status in Status.values())
            }
}

