package Consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig; //хранит конфигурационные свойства клиента Apache Kafka
import org.apache.kafka.common.serialization.StringDeserializer; // десериализатор для преобразования байтовых значений обратно в строки
import org.springframework.beans.factory.annotation.Value; // позволяет извлекать значения свойств из файла конфигурации (обычно application.properties)
import org.springframework.context.annotation.Bean; // метка для объявления бинов (компонентов), которые будут управляться контейнером Spring
import org.springframework.context.annotation.Configuration; // показывает, что класс предназначен для предоставления настроек приложения
import org.springframework.kafka.annotation.EnableKafka; // активирует поддержку Kafka в приложении
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory; // фабрика контейнеров для обработки асинхронных запросов сообщений Kafka
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory; // фабричный класс для создания потребителей Kafka

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    // Используя аннотацию @Value, получаем значение свойства "kafka.bootstrap.servers" из файла конфигурации (application.properties).
    // Обычно это адрес сервера Kafka брокера.
//    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;
    // Создается объект типа Map<String, Object> с конфигурационными параметрами для потребителя Kafka
    @Bean
    public Map<String, Object> consumerConfigs() {
        return new HashMap<>() {{
            String bootstrapServers = "localhost:9092";
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); // адреса серверов Kafka
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // класс-десериализатор ключей сообщений
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // класс-десериализатор значений сообщений
            put(ConsumerConfig.GROUP_ID_CONFIG, "console-consumer-31363"); // идентификатор группы потребителей
        }};
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
