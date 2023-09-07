package de.wirschiffendas.motor_analyzser

import kotlin.random.Random
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class MotorAnalyzer(status_notification_fun: (id:Int, String, String) -> Unit) {
    val status_notification_fun = status_notification_fun
    val logger: Logger = LoggerFactory.getLogger(KafkaMessanger::class.java)

    // Execute analysis for each equipment in parallel via coroutines
    fun executeAnalysis() = runBlocking {
        logger.info("Executing analysis...")

        val gearboxAnalyzer = GearboxAnalyzer()
        val engineAnalyzer = EngineAnalyzer()
        val startingSystemAnalyzer = StartingSystemAnalyzer()

        launch(Dispatchers.Default) {
            status_notification_fun(123,"GEARBOX",Status.RUNNING.toString())
            gearboxAnalyzer.executeAnalysis()
            status_notification_fun(123, "GEARBOX", gearboxAnalyzer.status.toString())
        }

        launch(Dispatchers.Default) {
            status_notification_fun(123,"ENGINE",Status.RUNNING.toString())
            engineAnalyzer.executeAnalysis()
            status_notification_fun(123,"ENGINE",startingSystemAnalyzer.status.toString())
        }

        launch(Dispatchers.Default) {
            status_notification_fun(123,"STARTING_SYSTEM",Status.RUNNING.toString())
            startingSystemAnalyzer.executeAnalysis()
            status_notification_fun(123,"STARTING_SYSTEM",startingSystemAnalyzer.status.toString())
        }
    }
}

class GearboxAnalyzer {
    var status: Status = Status.NOT_STARTED

    suspend fun executeAnalysis() {
        status = Status.RUNNING

        status = randomlyFail()
    }
}

class EngineAnalyzer {
    var status: Status = Status.NOT_STARTED

    suspend fun executeAnalysis() {
        status = Status.RUNNING

        status = randomlyFail()
    }
}

class StartingSystemAnalyzer {
    var status: Status = Status.NOT_STARTED

    suspend fun executeAnalysis() {
        status = Status.RUNNING

        status = randomlyFail()
    }
}

suspend fun randomlyFail(): Status {
    val probability = 0.2 // Fail probability
    delay(timeMillis = 2000L) // Simulate computation time

    if (Random.nextDouble() < probability) {
        println("Triggered! (20% probability)")

        return Status.FAILED
    } else {
        println("Not triggered! (80% probability)")

        return Status.SUCCESS
    }
}

suspend fun suspendFunction(): String {
    delay(1000L)
    return "Suspended function completed"
}
