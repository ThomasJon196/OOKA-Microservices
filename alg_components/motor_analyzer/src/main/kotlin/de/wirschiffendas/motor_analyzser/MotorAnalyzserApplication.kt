package de.wirschiffendas.motor_analyzser

import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

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

        // Simulated configuration object
        val config_message = JSONObject()
        config_message.put("request_id", 123)
        config_message.put(
                "category_motor",
                mapOf("GEARBOX" to "diesel", "ENGINE" to "v8", "STARTING_SYSTEM" to "electric")
        )

        // Send message to kafka topic
        val kafkaMessanger = KafkaMessanger()
        kafkaMessanger.produceTestConfig(config_message.toString())

        return ResponseEntity("Kafka Producer executed.", HttpStatus.OK)
    }

    @GetMapping("/startConsumingKafka")
    fun startConsumingKafka(): ResponseEntity<String> {
        logger.info("Starting kafka consumer.")

        // Init KafkaMessanger
        val kafkaMessanger = KafkaMessanger()

        // Init MotorAnalyzer
        val motorAnalyzer = MotorAnalyzer(kafkaMessanger::produce_statusmessage)
        kafkaMessanger.consume(topic = "ooka_jonasweber_motor", motorAnalyzer::executeAnalysis)

        return ResponseEntity("Kafka Consumer running.", HttpStatus.OK)
    }
}
