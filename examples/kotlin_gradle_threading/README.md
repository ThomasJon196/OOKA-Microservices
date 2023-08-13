# Kotlin - Threading

This repo is an example for:

Running two coroutines and wait for both to finish.

Coroutines are used for theading as they are more lightweight. 



```kotlin
fun main() = runBlocking { // this: CoroutineScope
    var job = launch { // launch a new coroutine and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    println("Hello") // main coroutine continues while a previous one is delayed
    job.join() // wait until child coroutine completes
    println("Done") 
}
```

`launch()`: Cooroutine builder. Launces a new coroutine concurrently with rest of the code

`delay()`: Non-Blocking delay.

`runBlocking()`: Coroutine builder. Bridge between coroutine & non-couroutine world.

    You will often see runBlocking used like that at the very top-level of the application and quite rarely inside the real code, as threads are expensive resources and blocking them is inefficient and is often not desired.

`job.join()`: Wait until child coroutine completes



## Sources

- https://kotlinlang.org/docs/coroutines-basics.html#extract-function-refactoring