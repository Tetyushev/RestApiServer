package ru.rctikk.server.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

public class TensorRequest {
    @JsonProperty("shape")
    private int[] shape;

    @JsonProperty("data")
    private double[] data;

    @JsonProperty("dtype")
    private String dtype;

    @JsonProperty("name")
    private String name;

    // Конструкторы
    public TensorRequest() {}

    // Геттеры и сеттеры
    public int[] getShape() { return shape; }
    public void setShape(int[] shape) { this.shape = shape; }

    public double[] getData() { return data; }
    public void setData(double[] data) { this.data = data; }

    public String getDtype() { return dtype; }
    public void setDtype(String dtype) { this.dtype = dtype; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Валидация
    public boolean isValid() {
        return shape != null && data != null &&
                shape.length > 0 && data.length > 0;
    }
}