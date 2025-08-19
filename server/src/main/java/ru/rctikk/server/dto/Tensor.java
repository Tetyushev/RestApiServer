package ru.rctikk.server.dto;

import java.util.Arrays;

/**
 * Класс тензора для работы с многомерными массивами данных
 */
public class Tensor {
    private double[] data;
    private int[] shape;
    private int[] strides;
    private int size;

    /**
     * Конструктор тензора с заданной формой
     *
     * @param shape форма тензора [dim1, dim2, dim3, ...]
     */
    public Tensor(int... shape) {
        if (shape == null || shape.length == 0) {
            throw new IllegalArgumentException("Shape cannot be null or empty");
        }

        this.shape = Arrays.copyOf(shape, shape.length);
        this.size = calculateSize(shape);
        this.data = new double[size];
        this.strides = calculateStrides(shape);
    }

    /**
     * Конструктор тензора с заданными данными
     *
     * @param data  данные тензора
     * @param shape форма тензора
     */
    public Tensor(double[] data, int... shape) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        this.shape = Arrays.copyOf(shape, shape.length);
        this.size = calculateSize(shape);

        if (data.length != this.size) {
            throw new IllegalArgumentException("Data size doesn't match shape");
        }

        this.data = Arrays.copyOf(data, data.length);
        this.strides = calculateStrides(shape);
    }

    /**
     * Вычисление общего размера тензора
     */
    private int calculateSize(int[] shape) {
        int result = 1;
        for (int dim : shape) {
            if (dim <= 0) {
                throw new IllegalArgumentException("Dimensions must be positive");
            }
            result *= dim;
        }
        return result;
    }

    /**
     * Вычисление шагов (strides) для каждого измерения
     */
    private int[] calculateStrides(int[] shape) {
        int[] strides = new int[shape.length];
        int stride = 1;
        for (int i = shape.length - 1; i >= 0; i--) {
            strides[i] = stride;
            stride *= shape[i];
        }
        return strides;
    }

    /**
     * Получение индекса в одномерном массиве по многомерным координатам
     */
    private int getIndex(int... indices) {
        if (indices.length != shape.length) {
            throw new IllegalArgumentException("Number of indices must match tensor dimensions");
        }

        int index = 0;
        for (int i = 0; i < indices.length; i++) {
            if (indices[i] < 0 || indices[i] >= shape[i]) {
                throw new IndexOutOfBoundsException("Index out of bounds");
            }
            index += indices[i] * strides[i];
        }
        return index;
    }

    /**
     * Установка значения по индексу
     */
    public void set(double value, int... indices) {
        int index = getIndex(indices);
        data[index] = value;
    }

    /**
     * Получение значения по индексу
     */
    public double get(int... indices) {
        int index = getIndex(indices);
        return data[index];
    }

    /**
     * Получение формы тензора
     */
    public int[] getShape() {
        return Arrays.copyOf(shape, shape.length);
    }

    /**
     * Получение количества измерений
     */
    public int getDimensions() {
        return shape.length;
    }

    /**
     * Получение общего размера
     */
    public int getSize() {
        return size;
    }

    /**
     * Получение данных в виде массива
     */
    public double[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    /**
     * Заполнение тензора заданным значением
     */
    public void fill(double value) {
        Arrays.fill(data, value);
    }

    /**
     * Создание тензора с нулями
     */
    public static Tensor zeros(int... shape) {
        return new Tensor(shape);
    }

    /**
     * Создание тензора с единицами
     */
    public static Tensor ones(int... shape) {
        Tensor tensor = new Tensor(shape);
        tensor.fill(1.0);
        return tensor;
    }

    /**
     * Создание тензора с случайными значениями
     */
    public static Tensor random(int... shape) {
        Tensor tensor = new Tensor(shape);
        for (int i = 0; i < tensor.size; i++) {
            tensor.data[i] = Math.random();
        }
        return tensor;
    }

    /**
     * Сложение тензоров поэлементно
     */
    public Tensor add(Tensor other) {
        if (!Arrays.equals(this.shape, other.shape)) {
            throw new IllegalArgumentException("Tensor shapes must match for addition");
        }

        Tensor result = new Tensor(this.shape);
        for (int i = 0; i < this.size; i++) {
            result.data[i] = this.data[i] + other.data[i];
        }
        return result;
    }

    /**
     * Умножение тензора на скаляр
     */
    public Tensor multiply(double scalar) {
        Tensor result = new Tensor(this.shape);
        for (int i = 0; i < this.size; i++) {
            result.data[i] = this.data[i] * scalar;
        }
        return result;
    }

    /**
     * Матричное умножение (для 2D тензоров)
     */
    public Tensor matmul(Tensor other) {
        if (this.shape.length != 2 || other.shape.length != 2) {
            throw new IllegalArgumentException("Matrix multiplication requires 2D tensors");
        }
        if (this.shape[1] != other.shape[0]) {
            throw new IllegalArgumentException("Matrix dimensions don't match for multiplication");
        }

        int rows = this.shape[0];
        int cols = other.shape[1];
        int common = this.shape[1];

        Tensor result = new Tensor(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double sum = 0;
                for (int k = 0; k < common; k++) {
                    sum += this.get(i, k) * other.get(k, j);
                }
                result.set(sum, i, j);
            }
        }
        return result;
    }

    /**
     * Транспонирование (для 2D тензоров)
     */
    public Tensor transpose() {
        if (this.shape.length != 2) {
            throw new IllegalArgumentException("Transpose requires 2D tensor");
        }

        Tensor result = new Tensor(this.shape[1], this.shape[0]);
        for (int i = 0; i < this.shape[0]; i++) {
            for (int j = 0; j < this.shape[1]; j++) {
                result.set(this.get(i, j), j, i);
            }
        }
        return result;
    }

    /**
     * Получение подтензора (среза)
     */
    public Tensor slice(int dimension, int start, int end) {
        if (dimension < 0 || dimension >= shape.length) {
            throw new IllegalArgumentException("Invalid dimension");
        }
        if (start < 0 || end > shape[dimension] || start >= end) {
            throw new IllegalArgumentException("Invalid slice range");
        }

        int[] newShape = Arrays.copyOf(shape, shape.length);
        newShape[dimension] = end - start;

        Tensor result = new Tensor(newShape);

        // Реализация среза (упрощенная)
        // В реальной реализации потребуется более сложная логика
        return result;
    }

    /**
     * Преобразование в строку
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tensor(shape=").append(Arrays.toString(shape)).append(")\n");
        if (shape.length == 1) {
            sb.append(Arrays.toString(data));
        } else if (shape.length == 2) {
            sb.append("Matrix:\n");
            for (int i = 0; i < shape[0]; i++) {
                sb.append("[");
                for (int j = 0; j < shape[1]; j++) {
                    sb.append(String.format("%.4f", get(i, j)));
                    if (j < shape[1] - 1) sb.append(", ");
                }
                sb.append("]\n");
            }
        } else {
            sb.append("Data: ").append(Arrays.toString(data));
        }
        return sb.toString();
    }

    /**
     * Проверка равенства тензоров
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Tensor tensor = (Tensor) obj;
        return Arrays.equals(shape, tensor.shape) &&
                Arrays.equals(data, tensor.data);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(shape);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
