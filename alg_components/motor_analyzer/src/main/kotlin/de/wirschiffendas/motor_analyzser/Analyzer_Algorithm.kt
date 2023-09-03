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
    var status: Status = Status.PENDING
    val logger: Logger = LoggerFactory.getLogger(KafkaMessanger::class.java)

    fun executeAnalysis() = runBlocking {
        status = Status.RUNNING

        logger.info("Executing analysis...")

        val gearboxAnalyzer = GearboxAnalyzer()
        val engineAnalyzer = EngineAnalyzer()
        val startingSystemAnalyzer = StartingSystemAnalyzer()

        launch(Dispatchers.Default) {
            gearboxAnalyzer.executeAnalysis()
            status_notification_fun(123,"gearboxAnalyzer",startingSystemAnalyzer.status.toString())
        }

        launch(Dispatchers.Default) {
            engineAnalyzer.executeAnalysis()
            status_notification_fun(123,"engineAnalyzer",startingSystemAnalyzer.status.toString())
        }

        launch(Dispatchers.Default) {
            startingSystemAnalyzer.executeAnalysis()
            status_notification_fun(123,"startingSystem",startingSystemAnalyzer.status.toString())
        }

        // Send status to Kafka
        // kafkaTemplate.send("topic", status)

        status = Status.PENDING
    }
}

class GearboxAnalyzer {
    var status: Status = Status.PENDING

    suspend fun executeAnalysis() {
        status = Status.RUNNING

        status = randomlyFail()
    }
}

class EngineAnalyzer {
    var status: Status = Status.RUNNING

    suspend fun executeAnalysis() {
        status = Status.RUNNING

        status = randomlyFail()

        // Send status to Kafka
        // kafkaTemplate.send("topic", status)

        status = Status.PENDING
    }
}

class StartingSystemAnalyzer {
    var status: Status = Status.RUNNING

    suspend fun executeAnalysis() {
        status = Status.RUNNING

        status = randomlyFail()

        // Send status to Kafka
        // kafkaTemplate.send("topic", status)

        status = Status.PENDING
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

        return Status.OK
    }
}

suspend fun suspendFunction(): String {
    delay(1000L)
    return "Suspended function completed"
}
