package com.example.demo

import java.util.UUID
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@SpringBootApplication class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

// Class of type data
data class Message(val id: String?, val text: String)

@RestController
class MessageController(val service: MessageService) {
    @GetMapping("/demo/")
    fun demo() =
            listOf(
                    Message("1", "Hello!"),
                    Message("2", "Bonjour!"),
                    Message("3", "Privet!"),
            )

    @GetMapping("/") fun index(): List<Message> = service.findMessages()

    @PostMapping("/")
    fun post(@RequestBody message: Message) {
        service.save(message)
    }

    @GetMapping("/{id}")
    fun index(@PathVariable id: String): List<Message> = service.findMessageById(id)

    @GetMapping("/query/") fun query_endpoint(@RequestParam("name") name: String) = "Hello, $name!"
}

// Dependency injection for classes of type Service
// provides Databased Access
@Service
class MessageService(val db: JdbcTemplate) {

    fun findMessages(): List<Message> =
            db.query("select * from messages") { response, _ ->
                Message(response.getString("id"), response.getString("text"))
            }

    fun findMessageById(id: String): List<Message> =
            db.query("select * from messages where id = ?", id) { response, _ ->
                Message(response.getString("id"), response.getString("text"))
            }

    fun save(message: Message) {
        val id = message.id ?: UUID.randomUUID().toString()
        db.update("insert into messages values ( ?, ? )", id, message.text)
    }
}
