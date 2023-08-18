package de.wirschiffendas.motor_analyzser

import kotlin.random.Random
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MotorAnalyzer {
    var status: Status = Status.PENDING

    fun executeAnalysis() = runBlocking{
        status = Status.RUNNING

        val gearboxAnalyzer = GearboxAnalyzer()
        val engineAnalyzer = EngineAnalyzer()
        val startingSystemAnalyzer = StartingSystemAnalyzer()

        launch(Dispatchers.Default) { gearboxAnalyzer.executeAnalysis() }

        launch(Dispatchers.Default) { engineAnalyzer.executeAnalysis() }

        launch(Dispatchers.Default) { startingSystemAnalyzer.executeAnalysis() }

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

        // Send status to Kafka
        // kafkaTemplate.send("topic", status)

        status = Status.PENDING
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
    val probability = 0.3 // Fail probability
    delay(timeMillis = 2000L) // Simulate computation time

    if (Random.nextDouble() < probability) {
        println("Triggered! (30% probability)")

        return Status.FAILED
    } else {
        println("Not triggered! (70% probability)")

        return Status.OK
    }
}

suspend fun suspendFunction(): String {
    delay(1000L)
    return "Suspended function completed"
}
