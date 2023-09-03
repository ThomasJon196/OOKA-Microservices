package de.wirschiffendas.motor_analyzser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import org.slf4j.Logger
import org.slf4j.LoggerFactory

@SpringBootApplication class MotorAnalyzserApplication

fun main(args: Array<String>) {
    runApplication<MotorAnalyzserApplication>(*args)
}

@RestController
class MessageController() {
    val logger: Logger = LoggerFactory.getLogger(MessageController::class.java)


    @GetMapping("/healthCheck") fun test() = "I am alive!"

    @GetMapping("/simulateNewConfig")
    fun startProducingKafka(): ResponseEntity<String> {
        logger.info("Started Kafka producer.")
    

        val kafkaMessanger = KafkaMessanger("")
        val message = kafkaMessanger.buildKafkaResponse(123, "equipmentName", "equipmentStatus")
        kafkaMessanger.produceTestConfig(message)

        return ResponseEntity("Kafka Producer executed.", HttpStatus.OK)
    }

    @GetMapping("/startConsumingKafka")
    fun startConsumingKafka(): ResponseEntity<String> {
        logger.info("Starting kafka consumer.")    

        val kafkaMessanger = KafkaMessanger("")

        val motorAnalyzer = MotorAnalyzer({ requestId, equipmentName, status -> kafkaMessanger.produce_statusmessage(requestId, equipmentName, status) })
        kafkaMessanger.consume(topic = "ooka_jonasweber_motor") { motorAnalyzer.executeAnalysis() }

        return ResponseEntity("Kafka Consumer running.", HttpStatus.OK)
    }
}



