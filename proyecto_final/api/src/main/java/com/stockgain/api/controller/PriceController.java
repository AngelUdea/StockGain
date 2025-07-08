package com.stockgain.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/price")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen
public class PriceController {

    // Inyecta la clave de API desde application.properties
    @Value("${alphavantage.api.key}")
    private String apiKey;

    // Un caché en memoria simple para guardar los precios temporalmente.
    // ConcurrentHashMap es seguro para usar en un entorno con múltiples peticiones.
    private final Map<String, CachedPrice> priceCache = new ConcurrentHashMap<>();

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/{ticker}")
    public ResponseEntity<?> getStockPrice(@PathVariable String ticker) {
        String upperCaseTicker = ticker.toUpperCase();

        // 1. Revisar si tenemos un precio válido en el caché
        if (priceCache.containsKey(upperCaseTicker)) {
            CachedPrice cached = priceCache.get(upperCaseTicker);
            // Si el precio tiene menos de 15 minutos, lo devolvemos
            if (cached.isStillValid()) {
                System.out.println("Devolviendo precio desde CACHÉ para: " + upperCaseTicker);
                return ResponseEntity.ok(cached.getData());
            }
        }

        // 2. Si no está en el caché o es muy viejo, lo pedimos a la API externa
        System.out.println("Pidiendo precio a la API de Alpha Vantage para: " + upperCaseTicker);
        String apiUrl = String.format(
            "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=%s",
            upperCaseTicker, apiKey
        );

        try {
            // Hacemos la petición a la API de Alpha Vantage
            Map<String, Object> apiResponse = restTemplate.getForObject(apiUrl, Map.class);

            // Verificamos si la respuesta contiene el objeto 'Global Quote'
            if (apiResponse == null || !apiResponse.containsKey("Global Quote")) {
                 // Esto puede pasar si la clave es inválida o se alcanzó el límite de peticiones
                System.err.println("Respuesta inválida de Alpha Vantage: " + apiResponse);
                return ResponseEntity.status(503).body("El servicio de precios no está disponible o la clave es inválida.");
            }
            
            Map<String, String> globalQuote = (Map<String, String>) apiResponse.get("Global Quote");

            // Verificamos si el objeto tiene un precio
            if (globalQuote.isEmpty() || globalQuote.get("05. price") == null) {
                System.err.println("No se encontró precio para el ticker: " + upperCaseTicker);
                return ResponseEntity.notFound().build();
            }

            // 3. Guardamos la respuesta exitosa en el caché
            priceCache.put(upperCaseTicker, new CachedPrice(apiResponse));

            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            System.err.println("Error al contactar la API de Alpha Vantage: " + e.getMessage());
            return ResponseEntity.status(500).body("Error interno al obtener el precio del activo.");
        }
    }

    // Clase interna para manejar el objeto en caché
    private static class CachedPrice {
        private final Map<String, Object> data;
        private final LocalDateTime timestamp;

        public CachedPrice(Map<String, Object> data) {
            this.data = data;
            this.timestamp = LocalDateTime.now();
        }

        public Map<String, Object> getData() {
            return data;
        }

        // Consideramos el precio válido por 15 minutos
        public boolean isStillValid() {
            return timestamp.isAfter(LocalDateTime.now().minusMinutes(15));
        }
    }
}
