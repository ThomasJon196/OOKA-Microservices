package de.wirschiffendas.motor_analyzser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication class MotorAnalyzserApplication

fun main(args: Array<String>) {
    runApplication<MotorAnalyzserApplication>(*args)
}

@RestController
class MessageController() {
    @GetMapping("/test") fun test() = "Hello"

    @PostMapping("/kafkaNotification")
    fun kafkaNotification(@RequestBody configuration: Configuration): ResponseEntity<String> {
		println("Configuration: $configuration")

        // Run analysis
        val motorAnalyzer = MotorAnalyzer()
        motorAnalyzer.executeAnalysis()

        return ResponseEntity("Received notification", HttpStatus.OK)
    }
}
