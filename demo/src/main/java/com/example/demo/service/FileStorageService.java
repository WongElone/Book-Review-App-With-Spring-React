package com.example.demo.service;

import com.example.demo.exception.FileResourceNotFoundException;
import com.example.demo.exception.FileStorageException;
import com.example.demo.property.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;


@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        // init designated file storage location
        this.fileStorageLocation = Path.of(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        // create directory (/uploads) in designated location in case not exists
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory in designated location", ex);
        }
    }

    public String storeFile(MultipartFile file, String folderName) {
        // normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Check invalid characters in file's name
            if (fileName.contains("..")) {
                throw new FileStorageException("Filename contains invalid path sequence: " + fileName);
            }
            System.out.println("before set target location");
            Path targetFolderLocation = this.fileStorageLocation.resolve(folderName);

            // create directory (/uploads/{folderName}) in designated location in case not exists
            try {
                Files.createDirectories(targetFolderLocation);
            } catch (Exception ex) {
                throw new FileStorageException("Could not create the directory in designated location", ex);
            }
            Path targetLocation = targetFolderLocation.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName, ex);
        }
    }

    public Resource loadFileAsResource(String fileName, String folderName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(folderName).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileResourceNotFoundException("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileResourceNotFoundException("File not found: " + fileName, ex);
        }
    }
}
