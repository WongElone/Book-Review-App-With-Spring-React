package com.example.demo.controller;

import com.example.demo.annotation.ValidFile;
import com.example.demo.dto.UploadFileResponse;
import com.example.demo.property.FileStorageProperties;
import com.example.demo.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
@Validated // such that @ValidFile will work
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private FileStorageProperties fileStorageProperties;
    @PostMapping("/uploadFile/{folderName}")
    public UploadFileResponse uploadFile(@PathVariable @Pattern(regexp = "^[A-Za-z0-9]+$") String folderName,
                                         @RequestPart @NotNull @ValidFile MultipartFile file
    ) throws MethodArgumentNotValidException {
        String fileName = fileStorageService.storeFile(file, folderName);

//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                // the url of requesting file is with /downloadFile/ (check the @GetMapping below)
//                .path("/api/v1/downloadFile/")
//                .path(folderName + "/")
//                .path(fileName)
//                .toUriString();

        String fileDownloadRelativeUri = UriComponentsBuilder.newInstance()
                // the url of requesting file is with /downloadFile/ (check the @GetMapping below)
                .path("/api/v1/downloadFile/")
                .path(folderName + "/")
                .path(fileName)
                .toUriString();

//        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path(fileStorageProperties.getUploadDirFromStatic())
//                .path(folderName + "/")
//                .path(fileName)
//                .toUriString();

        String fileRelativeUri = UriComponentsBuilder.newInstance()
                .path("/" + fileStorageProperties.getUploadDirFromStatic())
                .path(folderName + "/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadRelativeUri, fileRelativeUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles/{folderName}")
    public List<UploadFileResponse> uploadMultipleFiles(@PathVariable @Pattern(regexp = "^[A-Za-z0-9]+$") String folderName,
                                                        @RequestPart @Size(max = 3) List<MultipartFile> files
    ) throws MethodArgumentNotValidException {
        return files.stream()
                .map(file -> {
                    try {
                        return uploadFile(folderName, file);
                    } catch (MethodArgumentNotValidException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.toList());
    }

    // by including :.+ after the fileName identifier, this path variable captures the entire remainder of the URL path after the downloadFile/ segment as the fileName parameter. This allows you to handle requests for files with arbitrary names and extensions, rather than being limited to a specific set of known file names or extensions.
    @GetMapping("/downloadFile/{folderName}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,
                                                 @PathVariable @Pattern(regexp = "^[A-Za-z0-9]+$") String folderName,
                                                 HttpServletRequest request) {
        // load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName, folderName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
