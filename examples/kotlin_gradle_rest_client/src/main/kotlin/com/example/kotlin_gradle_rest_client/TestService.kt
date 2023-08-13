package com.example.kotlin_gradle_rest_client

class TestableService {
    fun getDataFromDb(testParameter: String): String {
        // query database and return matching value
        println("I'm doing something!")
        return "I'm a value from the database!"
    }

    fun doSomethingElse(testParameter: String): String {
        return "I don't want to!"
    }
}