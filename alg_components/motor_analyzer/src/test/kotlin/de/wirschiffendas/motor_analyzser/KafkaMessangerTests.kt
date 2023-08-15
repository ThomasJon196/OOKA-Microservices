package de.wirschiffendas.motor_analyzser

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.api.extension.ExtendWith


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class KafkaMessangerTests
@Autowired
constructor(private val restTemplate: TestRestTemplate) {

    @LocalServerPort private var port: Int = 0

    @Test
    fun `test send message to own test endpoint`() {
        val kafkaMessanger = KafkaMessanger("http://localhost:" + port + "/test")
        val response = kafkaMessanger.sendMessage(topic="test", message="test")

        assertEquals("Hello", response)
    }
}