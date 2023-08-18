package de.wirschiffendas.motor_analyzser

import org.springframework.web.client.RestTemplate


class KafkaMessanger(val endpoint: String) {
    fun sendMessage(topic: String, message: String): String? {
        println("Sending message to $endpoint with topic $topic and message $message")
        
        val restTemplate = RestTemplate()
        val quote = restTemplate.getForObject(this.endpoint, String::class.java)

        return quote
    }
}