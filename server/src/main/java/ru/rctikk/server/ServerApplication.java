package ru.rctikk.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        System.out.println("Tensor Server started on http://localhost:8080");
        System.out.println("API endpoints:");
        System.out.println("  POST /api/tensor - receive tensor");
        System.out.println("  POST /api/tensor/with-metadata - receive tensor with metadata");
        System.out.println("  GET  /api/tensor/info - server info");
    }
}
