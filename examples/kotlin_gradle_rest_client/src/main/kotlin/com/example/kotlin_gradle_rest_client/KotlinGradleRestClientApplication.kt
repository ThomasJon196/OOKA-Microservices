package com.example.kotlin_gradle_rest_client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.client.RestTemplate
import com.fasterxml.jackson.annotation.JsonIgnoreProperties


// Data classes for receiving JSON data from the REST API
data class Response(val data: String)

fun main(args: Array<String>) {

	val quote = RestTemplate().getForObject("http://localhost:8080", String::class.java)
 
    //Print the response to the console
    println(quote)
}


