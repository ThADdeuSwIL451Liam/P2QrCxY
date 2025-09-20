// 代码生成时间: 2025-09-20 10:45:04
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class FileUnzipUtil {

    private static final String TEMP_DIR = "/tmp/zip/";

    public void unzipFile(MultipartFile file, String destinationDir) throws IOException {
        // 确保目录存在
        Files.createDirectories(Paths.get(destinationDir));

        // 临时文件路径
        String tempFilePath = TEMP_DIR + file.getOriginalFilename();

        try (InputStream is = file.getInputStream();
             FileOutputStream fos = new FileOutputStream(tempFilePath);
             ZipInputStream zis = new ZipInputStream(new FileInputStream(tempFilePath))) {

            // 复制文件到临时目录
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }

            // 解压文件
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String entryName = zipEntry.getName();
                File entryFile = new File(destinationDir + File.separator + entryName);
                if (zipEntry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    File parent = entryFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }

                    try (BufferedOutputStream bos = new BufferedOutputStream(
                            new FileOutputStream(entryFile))) {
                        byte[] bytes = new byte[1024];
                        int count;
                        while ((count = zis.read(bytes)) != -1) {
                            bos.write(bytes, 0, count);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (IOException e) {
            // 错误处理
            throw new IOException("Error when unzipping the file.", e);
        } finally {
            // 删除临时文件
            File tempFile = new File(tempFilePath);
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    // 可以添加压缩文件的方法

}