package de.wirschiffendas.motor_analyzser

data class Configuration(
    val request_id: String,
    val category_liquid: Map<String, String>,
    val category_motor: Map<String, String>,
    val category_software: Map<String, String>
)