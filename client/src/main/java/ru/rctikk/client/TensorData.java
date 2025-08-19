package ru.rctikk.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;

public class TensorData {
    @JsonProperty("shape")
    private int[] shape;

    @JsonProperty("data")
    private double[] data;

    @JsonProperty("dtype")
    private String dtype;

    @JsonProperty("name")
    private String name;

    // Конструкторы
    public TensorData() {}

    public TensorData(int[] shape, double[] data) {
        this.shape = shape != null ? Arrays.copyOf(shape, shape.length) : new int[0];
        this.data = data != null ? Arrays.copyOf(data, data.length) : new double[0];
        this.dtype = "float64";
        this.name = "tensor";
    }

    // Геттеры и сеттеры
    public int[] getShape() { return Arrays.copyOf(shape, shape.length); }
    public void setShape(int[] shape) { this.shape = Arrays.copyOf(shape, shape.length); }

    public double[] getData() { return Arrays.copyOf(data, data.length); }
    public void setData(double[] data) { this.data = Arrays.copyOf(data, data.length); }

    public String getDtype() { return dtype; }
    public void setDtype(String dtype) { this.dtype = dtype; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}