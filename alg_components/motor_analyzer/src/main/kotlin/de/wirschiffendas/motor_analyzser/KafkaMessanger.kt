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

class KafkaMessanger(val endpoint: String) {

    val logger: Logger = LoggerFactory.getLogger(KafkaMessanger::class.java)

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

    fun sendMessage(topic: String, message: String): String? {
        /*Deprecated method. */
        println("Sending message to $endpoint with topic $topic and message $message")

        val restTemplate = RestTemplate()
        val quote = restTemplate.getForObject(this.endpoint, String::class.java)

        return quote
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

    fun produce_statusmessage(message: String) {
        /*
        topics:
        ooka_jonasweber_exhaust
        ooka_jonasweber_liquid
        ooka_jonasweber_motor
        ooka_jonasweber_status
         */
        // Thread.currentThread().contextClassLoader = null

        val producer = KafkaProducer<String, String>(getKafkaProperties())

        producer.send(ProducerRecord("ooka_jonasweber_status", message))
        producer.close()
    }

    fun consume() {
        val consumer = KafkaConsumer<String, String>(getKafkaProperties())
        consumer.subscribe(listOf("ooka_jonasweber_motor"))

        logger.info("Starting Kafka consumer.")

        val kafkaConsumerThread = Thread {
            logger.info("Started Kafka consumer thread.")

            while (true) {
                val records = consumer.poll(Duration.ofMillis(100))
                for (record in records) {
                    logger.info(
                            "Received message in topic ${record.topic()} with offset ${record.offset()} and value ${record.value()}."
                    )
                    
                    // Run analysis
                    val motorAnalyzer = MotorAnalyzer()
                    motorAnalyzer.executeAnalysis()
                    // println("Consumed message.")
                    // println("offset = ${record.offset()}, key = ${record.key()}, value =
                    // ${record.value()}")
                }
            }
        }
        kafkaConsumerThread.start()
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
