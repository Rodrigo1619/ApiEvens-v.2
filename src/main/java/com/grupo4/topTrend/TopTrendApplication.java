package com.grupo4.topTrend;

import java.security.SecureRandom;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TopTrendApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TopTrendApplication.class);

        // Obtener el valor del puerto de la variable de entorno PORT
        String port = System.getenv("PORT");
        
        // Establecer el puerto en la aplicación
        if (port != null) {
            app.setDefaultProperties(Collections.singletonMap("server.port", port));
        }

        // Iniciar la aplicación
        app.run(args);
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(5, new SecureRandom());
	}

}
