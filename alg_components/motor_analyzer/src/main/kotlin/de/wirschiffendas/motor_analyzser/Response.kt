package de.wirschiffendas.motor_analyzser

enum class Status {
    OK,
    FAILED,
    RUNNING,
    PENDING
}

data class Response(val request_id: String, val equipment: Equipment)

data class Equipment(val name: String, val result: Status)
