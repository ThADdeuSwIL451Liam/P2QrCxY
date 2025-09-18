// 代码生成时间: 2025-09-18 20:56:04
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * A utility service for unzipping files.
 */
@Service
public class ZipUtilityService {

    private static final String TEMP_DIRECTORY = "temporary/"; // Temporary directory for unzipping

    /**
     * Unzips a file from a given path to a specified directory.
     * 
     * @param zipFilePath The path to the zip file.
     * @param outputDirectory The directory where the files will be unzipped.
     * @return A boolean indicating success or failure.
     * @throws IOException If an I/O error occurs.
     */
    public boolean unzipFile(String zipFilePath, String outputDirectory) throws IOException {
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String filePath = outputDirectory + File.separator + entry.getName();

                if (!entry.isDirectory()) {
                    extractFile(zipIn, filePath);
                } else {
                    // Create directory.
                    File dir = new File(filePath);
                    dir.mkdirs();
                }

                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
        return true;
    }

    /**
     * Extracts a file from the zip input stream and writes it to the file path.
     * 
     * @param zipIn The zip input stream.
     * @param filePath The file path to write to.
     * @throws IOException If an I/O error occurs.
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }

    /**
     * Ensures the temporary directory exists.
     * 
     * @throws IOException If an I/O error occurs.
     */
    private void ensureTempDirectoryExists() throws IOException {
        File tempDir = new File(TEMP_DIRECTORY);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
    }
}
