package ru.rctikk.client;

import ru.rctikk.server.dto.Tensor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClientApplication {
    public static void main(String[] args) throws Exception {
        try {
            // Создание тензора
            Tensor tensor = Tensor.random(2, 3, 4); // 3D тензор

            // Преобразование в формат для отправки
            TensorData tensorData = new TensorData(
                    tensor.getShape(),
                    tensor.getData()
            );

            // Создание клиента
            TensorClient client = new TensorClient("http://localhost:8080");

            // Отправка тензора
            String response = client.sendTensor(tensorData);
            System.out.println("Server response: " + response);

        } catch (Exception e) {
            System.err.println("Error sending tensor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}