package Consumer;

import Message.Message; // Класс модели сообщения
import Message.MessageRepository; // Репозиторий сообщений
import lombok.RequiredArgsConstructor; // Аннотация Lombok для автоматического конструктора с аргументами
import lombok.extern.slf4j.Slf4j; // Аннотация Lombok для автоинициализации логгера SLF4J
import org.springframework.kafka.annotation.KafkaListener; // Аннотация Spring для слушателя Kafka
import org.springframework.messaging.handler.annotation.Payload; // Аннотация Spring для извлечения тела сообщения
import org.springframework.stereotype.Component; // Аннотация Spring для компонента

// Компонент класса с автоматически создаваемым конструктором аргументов и логгером
@Component
@Slf4j
@RequiredArgsConstructor
public class Listener {
    //  Репозиторий для сохранения сообщений
    private final MessageRepository messageRepository;
    // Обработчик сообщений от топика Kafka
    @KafkaListener(topics = "TestTopic", groupId = "console-consumer-66482")
    public void handleMessage(@Payload String payload) {
        // Логируем входящее сообщение
        log.info("Received Kafka message: {}", payload);

        try {
            var parts = payload.split(",");

            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid format");
            }

            var msgId = parts[0].trim();
            var head = parts[1].trim();

            var currentTimeMillis = System.currentTimeMillis(); // Текущие миллисекунды Unix-времени

            var message = new Message();
            message.setMsgId(msgId);
            message.setHead(head);
            message.setTimeRq(currentTimeMillis); // Сохраняем время прихода сообщения

            messageRepository.save(message);

            log.info("Сообщение сохранено с ID={} и значением Head={}. Время получения: {}",
                    msgId, head, currentTimeMillis);
        } catch (Exception e) {
            log.error("Ошибка обработки сообщения: ", e);
        }
    }
}