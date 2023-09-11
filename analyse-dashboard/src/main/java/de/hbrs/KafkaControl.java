package de.hbrs;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;
import java.util.function.Consumer;

public class KafkaControl {

    // Kafka Constants
    private static final String KAFKA_BROKER = "sepp-kafka.inf.h-brs.de:9092";

    private static final String KEY_SERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringSerializer";

    private static final String VALUE_SERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringSerializer";

    private static final String KEY_DESERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringDeserializer";

    private static final String VALUE_DESERIALIZER_CLASS = "org.apache.kafka.common.serialization.StringDeserializer";

    private static Properties getKafkaProperties() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KEY_SERIALIZER_CLASS);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VALUE_SERIALIZER_CLASS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KEY_DESERIALIZER_CLASS);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, VALUE_DESERIALIZER_CLASS);
        // for consumer
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer_ooka_jonasweber");
        return props;
    }

    public static void distributeConfiguration(String jsonConfiguration) {
        /*
        topics:
        ooka_jonasweber_exhaust
        ooka_jonasweber_liquid
        ooka_jonasweber_motor
        ooka_jonasweber_status
         */

        System.out.println("Sende Konfiguration an Kafka: " + jsonConfiguration);
        Thread.currentThread().setContextClassLoader(null);
        KafkaProducer<String, String> producer = new KafkaProducer(getKafkaProperties());

        producer.send(new ProducerRecord<>("ooka_jonasweber_exhaust", jsonConfiguration));
        producer.send(new ProducerRecord<>("ooka_jonasweber_liquid", jsonConfiguration));
        producer.send(new ProducerRecord<>("ooka_jonasweber_motor", jsonConfiguration));
        producer.close();
    }

    private static StatusConsumer statusConsumerThread = null;

    public static void startStatusConsumer(Consumer<String> messageConsumer) {
        if (statusConsumerThread == null) {
            statusConsumerThread = new StatusConsumer(messageConsumer);
            statusConsumerThread.start();

        }
    }

    public static void stopStatusConsumer() {
        if (statusConsumerThread != null && statusConsumerThread.isAlive()) {
            statusConsumerThread.interrupt();
            statusConsumerThread = null;
        }
    }

    private static class StatusConsumer extends Thread {

        Consumer<String> messageConsumer;
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getKafkaProperties());

        private StatusConsumer(Consumer<String> messageConsumer) {
            super();
            this.messageConsumer = messageConsumer;
        }

        @Override
        public void run() {
            super.run();
            consumer.subscribe(Arrays.asList("ooka_jonasweber_status"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    messageConsumer.accept(record.value());
                    //System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
                }
            }
        }

        @Override
        public void interrupt() {
            super.interrupt();
            consumer.close();
        }
    }


}
