package com.example.kotlin_gradle_rest_client

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate

// @SpringBootTest
// class KotlinGradleRestClientApplicationTests {

// 	@Test
// 	fun contextLoads() {
// 	}

// }


class KotlinGradleRestClientApplicationTests {

    @Test fun contextLoads() {}

    @Test
    fun testResponse() {
        val quote = RestTemplate().getForObject("http://localhost:8080", String::class.java)
        assert(quote == "Hello World")
    }

    @Test
    fun givenServiceMock_whenCallingMockedMethod_thenCorrectlyVerified() {
        // given
        val service = mockk<TestableService>()
        every { service.getDataFromDb("Expected Param") } returns "Expected Output"

        // when
        val result = service.getDataFromDb("Expected Param")

        // then
        verify { service.getDataFromDb("Expected Param") }
        assertEquals("Expected Output", result)
    }
}
