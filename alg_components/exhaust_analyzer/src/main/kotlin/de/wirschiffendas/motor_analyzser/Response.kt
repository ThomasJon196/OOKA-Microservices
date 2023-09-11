package de.wirschiffendas.motor_analyzser

enum class Status {
    RUNNING,
    FAILED,
    NOT_STARTED, // send once on startup
    SUCCESS
}

data class Response(val request_id: String, val equipment: Equipment)

data class Equipment(val name: String, val result: Status)


/*
Equipment namen:

OIL
FUEL
GEARBOX
ENGINE
STARTING_SYSTEM
TEST_MONITORING
TEST_GEARBOX

*/