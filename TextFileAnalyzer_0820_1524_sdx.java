// 代码生成时间: 2025-08-20 15:24:43
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class TextFileAnalyzer {
    
    @Value("classpath:sample.txt")
    private ResourceLoader resourceLoader;

    // A method to read text file and return the content
    public String readTextFile(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource(filePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("
"));
        }
    }

    // Exception handler for IOException
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.internalServerError().body("An error occurred while reading the file: " + e.getMessage());
    }

    // Rest Controller to upload and analyze text file
    @RestController
    public class TextFileAnalysisController {

        @GetMapping("/analyze")
        public ResponseEntity<String> analyzeTextFile(@RequestParam("file") MultipartFile file) {
            try {
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body("File is empty");
                }
                String content = new String(file.getBytes());
                // Here you can add your analysis logic
                // For demonstration, we are just returning the content
                return ResponseEntity.ok().body("File content: " + content);
            } catch (IOException e) {
                return ResponseEntity.internalServerError().body("Error processing file: " + e.getMessage());
            }
        }
    }
}