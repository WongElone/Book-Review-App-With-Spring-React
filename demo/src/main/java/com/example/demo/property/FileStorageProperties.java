package com.example.demo.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    // mapping to file.upload-dir in application.properties file
    private String uploadDir;
    private String uploadDirFromStatic;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getUploadDirFromStatic() {
        return uploadDirFromStatic;
    }

    public void setUploadDirFromStatic(String uploadDirFromStatic) {
        this.uploadDirFromStatic = uploadDirFromStatic;
    }
}
