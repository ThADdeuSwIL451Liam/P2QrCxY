// 代码生成时间: 2025-08-29 02:16:22
package com.example.converter;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class DocumentConverterComponent {

    private static final String TEMP_DIR = "/tmp/";

    @GetMapping("/convert")
    public ResponseEntity<Resource> convertDocument(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File is empty or null.");
            }

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
            String newFilename = UUID.randomUUID().toString() + "." + extension;
            Path path = Paths.get(TEMP_DIR + newFilename);

            // Save the file to a temporary location
            Files.copy(file.getInputStream(), path);

            // Convert the file (this is where the actual conversion logic would go)
            // For the sake of example, we'll just read the file back
            InputStream inputStream = Files.newInputStream(path);
            ByteArrayResource resource = new ByteArrayResource(inputStream);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename="" + newFilename + """)
                    .body(resource);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.badRequest().body("Error processing the document: " + e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // Log the exception (logging framework would be used in a real application)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred: " + e.getMessage());
    }
}
