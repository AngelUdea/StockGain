package com.stockgain.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * APIAPPLICATION.JAVA
 * 
 * Clase principal de la aplicación Spring Boot para el backend de StockGain
 * 
 * Descripción:
 * Esta es la clase principal que inicializa la aplicación Spring Boot para el backend
 * de StockGain. Contiene el método main que arranca el servidor y configura
 * automáticamente todos los componentes de la aplicación.
 * 
 * Funcionalidades:
 * - Configuración automática de Spring Boot
 * - Inicialización del servidor embebido (Tomcat)
 * - Configuración de componentes y beans
 * - Escaneo automático de packages
 * - Configuración de seguridad y base de datos
 * 
 * Configuración automática incluye:
 * - Configuración de base de datos H2
 * - Configuración de seguridad JWT
 * - Configuración de controladores REST
 * - Configuración de repositories JPA
 * - Configuración de servicios
 * 
 * Puerto predeterminado: 8080
 * Base de datos: H2 (en memoria para desarrollo)
 * Seguridad: JWT (JSON Web Tokens)
 * 
 * @author StockGain Team
 * @version 1.0
 */
@SpringBootApplication
public class ApiApplication {

	/**
	 * Método principal que inicia la aplicación Spring Boot
	 * 
	 * @param args argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
