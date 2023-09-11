package de.wirschiffendas.motor_analyzser

import org.json.JSONObject

data class Configuration(
    val request_id: Int,
    val category_motor: Map<String, String>,
)