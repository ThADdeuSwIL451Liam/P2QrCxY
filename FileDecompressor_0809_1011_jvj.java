// 代码生成时间: 2025-08-09 10:11:28
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Spring Boot component for decompressing files.
 */
@Component
public class FileDecompressor {

    private static final String TEMP_DIRECTORY = "./tmp";

    public void decompressFile(MultipartFile file) throws IOException {
        // Create the temporary directory if it does not exist
        Files.createDirectories(Paths.get(TEMP_DIRECTORY));

        // Prepare the file path for the uploaded file
        Path filePath = Paths.get(TEMP_DIRECTORY, file.getOriginalFilename());

        // Save the uploaded file to the temporary directory
        try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
            fos.write(file.getBytes());
        }

        // Decompress the file
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(filePath.toFile()))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                // Prepare the file path for the decompressed file
                Path outPath = Paths.get(TEMP_DIRECTORY, zipEntry.getName());

                // Create the parent directory if it does not exist
                Files.createDirectories(outPath.getParent());

                // Extract the file
                if (zipEntry.isDirectory()) {
                    Files.createDirectories(outPath);
                } else {
                    try (FileOutputStream fos = new FileOutputStream(outPath.toFile())) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zis.read(buffer)) >= 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } finally {
            // Delete the uploaded file after decompression
            Files.deleteIfExists(filePath);
        }
    }
}
