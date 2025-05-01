package com.surendramaran.yolov8tflite;

import java.time.LocalDateTime;

public class Plant {
    private String diseaseName;
    private String imageBase64;
    private LocalDateTime detectionTime;
    private double confidence;

    // Конструктор
    public Plant(String diseaseName, String imageBase64, double confidence) {
        this.diseaseName = diseaseName;
        this.imageBase64 = imageBase64;
        this.detectionTime = detectionTime;
        this.confidence = confidence;
    }

    // Геттеры и сеттеры
    public String getDiseaseName() {
        return diseaseName;
    }

    public String getImageData() {
        return imageBase64;
    }

    public LocalDateTime getDetectionTime() {
        return detectionTime;
    }

    public double getConfidence() {
        return confidence;
    }
}
