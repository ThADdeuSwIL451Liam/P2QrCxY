// 代码生成时间: 2025-08-30 03:27:44
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RestController
public class BatchFileRenamer {

    private static final String UPLOAD_DIR = "./uploads/";

    @PostMapping("/renameFiles")
    public ResponseEntity<?> renameFiles(@RequestParam("files") List<MultipartFile> files,
                                         @RequestParam("newNames") List<String> newNames) {

        Map<String, Object> response = new HashMap<>();
        try {
            if (files.size() != newNames.size()) {
                response.put("error", "Number of files and new names must match.");
                return ResponseEntity.badRequest().body(response);
            }

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                String newName = newNames.get(i);
                renameFile(file, newName);
            }

            response.put("message", "Files renamed successfully.");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", "Error occurred while renaming files: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * Renames a single file.
     *
     * @param file The file to rename.
     * @param newName The new name for the file.
     * @throws IOException If an I/O error occurs.
     */
    private void renameFile(MultipartFile file, String newName) throws IOException {
        String originalName = file.getOriginalFilename();
        Path originalPath = Paths.get(UPLOAD_DIR + originalName);
        Path newPath = Paths.get(UPLOAD_DIR + newName + "." + originalName.split("\.").length > 1 ? originalName.split("\.")[newName.split("\.