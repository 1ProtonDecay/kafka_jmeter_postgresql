// Основной класс приложения
package Application;

import org.springframework.boot.SpringApplication; // базовый класс, обеспечивающий запуск приложений
import org.springframework.boot.autoconfigure.SpringBootApplication; // аннотация, используемая для автоматического конфигурирования компонентов приложения и запуска встроенного веб-контейнера

// Аннотация @SpringBootApplication означает автоматическую конфигурацию и запуск приложения
@SpringBootApplication
public class Application {
	// Точка входа в программу
	public static void main(String[] args) {
		// Запускаем приложение Spring Boot
		SpringApplication.run(Application.class, args);
	}
}