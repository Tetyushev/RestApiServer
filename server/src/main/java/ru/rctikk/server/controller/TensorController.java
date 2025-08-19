package ru.rctikk.server.controller;

import ru.rctikk.server.dto.TensorRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tensor")
@CrossOrigin(origins = "*")
public class TensorController {

    /**
     * Прием тензора
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> receiveTensor(
            @RequestBody TensorRequest tensorRequest) {

        try {
            // Валидация входных данных
            if (!tensorRequest.isValid()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", "error");
                errorResponse.put("message", "Invalid tensor data");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // Обработка тензора
            System.out.println("Received tensor:");
            System.out.println("Shape: " + java.util.Arrays.toString(tensorRequest.getShape()));
            System.out.println("Data length: " + tensorRequest.getData().length);
            System.out.println("Data type: " + tensorRequest.getDtype());

            // Здесь можно добавить логику обработки тензора
            // Например, преобразование в ваш класс Tensor

            // Ответ клиенту
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Tensor received successfully");
            response.put("received_shape", tensorRequest.getShape());
            response.put("received_elements", tensorRequest.getData().length);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to process tensor: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * Прием тензора с метаданными
     */
    @PostMapping("/with-metadata")
    public ResponseEntity<Map<String, Object>> receiveTensorWithMetadata(
            @RequestBody Map<String, Object> payload) {

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> tensorData = (Map<String, Object>) payload.get("tensor");
            String metadata = (String) payload.get("metadata");

            System.out.println("Received tensor with metadata:");
            System.out.println("Metadata: " + metadata);
            System.out.println("Tensor data: " + tensorData);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Tensor with metadata received");
            response.put("metadata_received", metadata);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Failed to process tensor with metadata: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    /**
     * Получение информации о сервере
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getServerInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("status", "running");
        info.put("api_version", "1.0");
        info.put("supported_operations", new String[]{"tensor_receive", "tensor_process"});
        return ResponseEntity.ok(info);
    }
}