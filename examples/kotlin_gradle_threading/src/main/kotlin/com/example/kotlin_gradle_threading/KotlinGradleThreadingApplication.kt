package com.example.kotlin_gradle_threading

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.Job

// @SpringBootApplication
// class KotlinGradleThreadingApplication

// fun main(args: Array<String>) {
// 	runApplication<KotlinGradleThreadingApplication>(*args)
// }

fun main(args: Array<String>) = runBlocking {
    // Create empty array
    var jobs = arrayOfNulls<Job>(2)

    // Launch two coroutines
	jobs[0] = launch(Dispatchers.Default) {
		fun_1()
	}
	jobs[1] = launch(Dispatchers.Default) {
		fun_2()
	}

    println("Outer-Routine finished.")
    
	//Join the two coroutines
	jobs[0]?.join()
	jobs[1]?.join()

    println("Outer-Routine finished waiting for Inner-Routine.")
}

// this is your first suspending function
suspend fun fun_1() {
    delay(1000L)
    println("${Thread.currentThread()} has executed fun_1. (Inner-Routine)")
}

suspend fun fun_2() {
    delay(1000L)
    println("${Thread.currentThread()} has executed fun_2 (Inner-Routine)")
}
