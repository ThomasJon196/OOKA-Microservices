package de.wirschiffendas.motor_analyzser

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MotorAnalyzserApplicationTests
@Autowired
constructor(private val restTemplate: TestRestTemplate) {

    @LocalServerPort private var port: Int = 0

    @Test fun contextLoads() {}

    @Test
    fun `kafkaNotification endpoint should contain configuration data class`() {
        // Given
        val configuration =
                Configuration(
                        request_id = "12345",
                        category_liquid = mapOf("Oil" to "Engine Oil", "fuel" to "Gasoline"),
                        category_motor =
                                mapOf(
                                        "gearbox" to "Automatic",
                                        "engine" to "V8",
                                        "starting system" to "Electric"
                                ),
                        category_software =
                                mapOf(
                                        "monitoring" to "Engine Performance",
                                        "fuel" to "Emission Control"
                                )
                )

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val requestEntity = HttpEntity(configuration, headers)

        // When
        val response =
                restTemplate.postForEntity(
                        "http://localhost:$port/kafkaNotification",
                        requestEntity,
                        String::class.java
                )

        // Then
        Assertions.assertEquals(200, response.statusCode.value())
        Assertions.assertNotNull(response.body)
        Assertions.assertEquals("Received notification", response.body)
    }

	@Test
    fun `test endpoint should return message Hello`() {
		// When
        val response = restTemplate.getForObject("http://localhost:$port/test", String::class.java)
		// Then
        Assertions.assertEquals("Hello", response)
    }
}
