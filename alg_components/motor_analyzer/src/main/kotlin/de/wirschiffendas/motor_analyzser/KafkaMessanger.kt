package de.wirschiffendas.motor_analyzser

import java.time.Duration
import java.util.*
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.client.RestTemplate

class KafkaMessanger() {

    val logger: Logger = LoggerFactory.getLogger(KafkaMessanger::class.java)
    var producer = KafkaProducer<String, String>(getKafkaProperties())
    var consumer = KafkaConsumer<String, String>(getKafkaProperties())


    // Hardcoded config. Could be inserted as KafkaConfig into Class
    object Config {
        const val KAFKA_BROKER = "sepp-kafka.inf.h-brs.de:9092"
        const val KEY_SERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringSerializer"
        const val VALUE_SERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringSerializer"
        const val KEY_DESERIALIZER_CLASS =
                "org.apache.kafka.common.serialization.StringDeserializer"
        const val VALUE_DESERIALIZER_CLASS =
                "org.apache.kafka.common.serialization.StringDeserializer"
        const val GROUP_ID = "console-consumer-ooka-jonasweber"
    }

    fun produceTestConfig(message: String) {
        /*
        topics:
        ooka_jonasweber_exhaust
        ooka_jonasweber_liquid
        ooka_jonasweber_motor
        ooka_jonasweber_status
         */
        // Thread.currentThread().contextClassLoader = null

        // val producer = KafkaProducer<String, String>(getKafkaProperties())

        producer.send(ProducerRecord("ooka_jonasweber_motor", message))
        producer.close()
    }

    fun produce_statusmessage(requestId: Int, equipmentName: String, status: String) {
        /*
        Write status of equipment to kafka topic.
        */

        /*
        topics:
        ooka_jonasweber_exhaust
        ooka_jonasweber_liquid
        ooka_jonasweber_motor
        ooka_jonasweber_status
         */

        // Thread.currentThread().contextClassLoader = null

        // Create JSON-Object with status message
        val config = JSONObject()
        config.put("request_id", requestId)
        val mapEquipment = HashMap<String, String>()
        mapEquipment["name"] = equipmentName
        mapEquipment["result"] = status
        config.put("equipment", mapEquipment)

        // Initialize KafkaProducer and send message
        val producer = KafkaProducer<String, String>(getKafkaProperties())
        producer.send(ProducerRecord("ooka_jonasweber_status", config.toString()))
        producer.close()

        logger.info(
                "Sent status message:  ${config.toString()} to Kafka topic ooka_jonasweber_status."
        )
    }

    fun consume(topic: String, routine_func: (Configuration) -> Unit = {}) {
        /*
        Subscribes to Kafka-Topic.
        Executes routine_func() on every received message.
         */
        // val consumer = KafkaConsumer<String, String>(getKafkaProperties())
        consumer.subscribe(listOf(topic))

        logger.info("Starting Kafka consumer.")

        val kafkaConsumerThread = Thread {
            logger.info("Started Kafka consumer thread.")

            while (true) {
                val records = consumer.poll(Duration.ofMillis(100))
                for (record in records) {
                    logger.info(
                            "Received message in topic ${record.topic()} with offset ${record.offset()} and value ${record.value()}."
                    )

                    // Parse json to Configuration
                    val json = JSONObject(record.value())
                    val request_id = json.getInt("request_id")
                    val category_motor = json.getJSONObject("category_motor")
                    val configuration =
                            Configuration(
                                    request_id = request_id,
                                    category_motor =
                                            category_motor.toMap().mapValues { it.value.toString() }
                            )
                    logger.info("Trasnformed into configuration object: ${configuration}}.")

                    // Call routine function
                    routine_func(configuration)
                }
            }
        }
        kafkaConsumerThread.start()
    }

    fun getKafkaProperties(): Properties {
        val props = Properties()
        props[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = Config.KAFKA_BROKER
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = Config.KEY_SERIALIZER_CLASS
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = Config.VALUE_SERIALIZER_CLASS
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = Config.KEY_DESERIALIZER_CLASS
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = Config.VALUE_DESERIALIZER_CLASS
        props[ConsumerConfig.GROUP_ID_CONFIG] = Config.GROUP_ID
        return props
    }

    fun buildKafkaResponse(requestId: Int, equipmentName: String, equipmentStatus: String): String {
        val config = JSONObject()
        config.put("request_id", requestId)

        val mapEquipment = HashMap<String, String>()
        mapEquipment["name"] = equipmentName
        mapEquipment["result"] = equipmentStatus
        config.put("equipment", mapEquipment)

        return config.toString()
    }
}
