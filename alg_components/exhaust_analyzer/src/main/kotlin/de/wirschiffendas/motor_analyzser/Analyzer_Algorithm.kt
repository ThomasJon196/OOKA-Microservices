package de.wirschiffendas.motor_analyzser

import kotlin.random.Random
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ExhaustAnalyzer(
        val status_notification_fun: (String, String, String) -> Unit = { _, _, _ -> }
) {
    val logger: Logger = LoggerFactory.getLogger(ExhaustAnalyzer::class.java)

    // Execute analysis for each equipment in parallel via coroutines
    fun executeAnalysis(configuration: Configuration) = runBlocking {
        logger.info("Executing analysis...")

        val gearboxAnalyzer = MonitoringAnalyzer("TEST_MONITORING", status_notification_fun, logger)
        val engineAnalyzer = GearboxAnalyzer("TEST_GEARBOX", status_notification_fun, logger)

        // Start all analyzers in parallel
        launch(Dispatchers.Default) { gearboxAnalyzer.executeAnalysis(configuration) }
        launch(Dispatchers.Default) { engineAnalyzer.executeAnalysis(configuration) }
    }
}

class MonitoringAnalyzer(
        val equipmentName: String,
        val status_notification_fun: (id: String, equipmentName: String, status: String) -> Unit,
        val logger: Logger
) {
    /* Simulated analyzer. Would contain complex algorithm. */
    var status: Status = Status.NOT_STARTED

    suspend fun updateStatus(configuration: Configuration, newStatus: Status) {
        val statusString = newStatus.toString()
        status_notification_fun(configuration.request_id, equipmentName, statusString)
        status = newStatus
    }

    suspend fun executeAnalysis(configuration: Configuration) {
        updateStatus(configuration, Status.RUNNING)

        delay(2000) // Simulate computation time

        if (randomlyFail()) {
            logger.info("Failed!")
            updateStatus(configuration, Status.FAILED)
        } else {
            logger.info("Success! (70% probability)")
            updateStatus(configuration, Status.SUCCESS)
        }
    }
}

class GearboxAnalyzer(
        val equipmentName: String,
        val status_notification_fun: (id: String, equipmentName: String, status: String) -> Unit,
        val logger: Logger
) {
    /* Simulated analyzer. Would contain complex algorithm. */
    var status: Status = Status.NOT_STARTED

    suspend fun updateStatus(configuration: Configuration, newStatus: Status) {
        val statusString = newStatus.toString()
        status_notification_fun(configuration.request_id, equipmentName, statusString)
        status = newStatus
    }

    suspend fun executeAnalysis(configuration: Configuration) {
        updateStatus(configuration, Status.RUNNING)

        delay(2000) // Simulate computation time

        if (randomlyFail()) {
            logger.info("Failed!")
            updateStatus(configuration, Status.FAILED)
        } else {
            logger.info("Success! (70% probability)")
            updateStatus(configuration, Status.SUCCESS)
        }
    }
}

suspend fun randomlyFail(): Boolean {
    val probability = 0.25 // Fail probability
    delay(2000) // Simulate computation time

    return Random.nextDouble() < probability
}
