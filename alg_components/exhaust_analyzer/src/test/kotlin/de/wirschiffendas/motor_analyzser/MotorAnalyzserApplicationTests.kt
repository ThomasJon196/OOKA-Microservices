package de.wirschiffendas.motor_analyzser

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MotorAnalyzserApplicationTests
@Autowired
constructor(private val restTemplate: TestRestTemplate) {

    @LocalServerPort private var port: Int = 0

    // @Test fun contextLoads() {}

    // @Test
    // fun `test endpoint should return message I am alive!`() {
    //     // When
    //     val response =
    //             restTemplate.getForObject("http://localhost:$port/healthCheck", String::class.java)

    //     // Then
    //     Assertions.assertEquals("I am alive!", response)
    // }

    // @Test
    // fun `simulateNewConfig endpoint should return message Kafka Producer executed`() {
    //     // When
    //     val response =
    //             restTemplate.getForObject(
    //                     "http://localhost:$port/simulateNewConfig",
    //                     String::class.java
    //             )

    //     // Then
    //     Assertions.assertEquals("Kafka Producer executed.", response)
    // }

    // @Test
    // fun `startConsumingKafka endpoint should return message Kafka Consumer running`() {
    //     // When
    //     val response =
    //             restTemplate.getForObject(
    //                     "http://localhost:$port/startConsumingKafka",
    //                     String::class.java
    //             )

    //     // Then
    //     Assertions.assertEquals("Kafka Consumer running.", response)
    // }
}
