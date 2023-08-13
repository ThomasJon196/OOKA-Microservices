// Create configuration data class with following properties:
// {
//     "request_id": <client-identifier>,
//     "category_liquid": 
//         {"Oil": <string>,
//          "fuel":<string>},
//     "category_motor": 
//         {"gearbox": <string>,
//          "engine": <string>,
//          "starting system": <string>,
//          "engine": <string>},
//     "category_software": 
//         {"monitoring": <string>,
//          "fuel": <string>},
// }
package de.wirschiffendas.motor_analyzser


data class Configuration(
    val request_id: String,
    val category_liquid: Map<String, String>,
    val category_motor: Map<String, String>,
    val category_software: Map<String, String>
)

// data class CategoryLiquid(
//     val Oil: String,
//     val fuel: String
// )

// data class CategoryMotor(
//     val gearbox: String,
//     val engine: String,
//     val starting_system: String
// )

// data class CategorySoftware(
//     val monitoring: String,
//     val fuel: String
// )