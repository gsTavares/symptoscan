package com.io.health.entity.enumerated;

import java.util.Arrays;

public enum DatasourceFileType {
    SYMPTOM("symptons"),
    DISEASE("diseases"),
    SYMPTOM_DISEASE("symptom_disease");

    private final String fileName;

    private DatasourceFileType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public static Boolean isValidFileType(String fileName) {
        String nameWithoutExtension = fileName.split("\\.")[0].toLowerCase();
        return Arrays.asList(DatasourceFileType.values()).stream()
                .anyMatch(dft -> dft.getFileName().toLowerCase().equals(nameWithoutExtension));
    }

    public static DatasourceFileType getDataSourceFileType(String fileName) {
        String nameWithoutExtension = fileName.split("\\.")[0].toLowerCase();
        return Arrays.asList(DatasourceFileType.values()).stream()
                .filter(dft -> dft.getFileName().toLowerCase().equals(nameWithoutExtension))
                .findFirst().get();
    }
}
