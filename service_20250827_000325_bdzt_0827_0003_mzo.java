// 代码生成时间: 2025-08-27 00:03:25
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class FileDecompressor {

    private static final String TEMP_DIR = "temp";

    public void decompressFile(MultipartFile file) throws IOException {
        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }

        // 创建临时目录
        createTempDirectory();

        // 将上传的文件保存到临时目录
        File tempFile = new File(TEMP_DIR + File.separator + file.getOriginalFilename());
        try (InputStream is = file.getInputStream();
             OutputStream os = new FileOutputStream(tempFile)) {
            // 将上传的文件数据写入临时文件
            is.transferTo(os);
        }

        // 检查文件是否为ZIP格式
        if (!file.getOriginalFilename().toLowerCase().endsWith(".zip")) {
            throw new IllegalArgumentException("只支持ZIP格式的压缩文件");
        }

        // 解压文件
        unzipFile(tempFile);
    }

    private void createTempDirectory() {
        try {
            Files.createDirectories(Paths.get(TEMP_DIR));
        } catch (IOException e) {
            throw new RuntimeException("无法创建临时目录", e);
        }
    }

    private void unzipFile(File zipFile) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = new File(TEMP_DIR + File.separator + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    new File(newFile.getParent()).mkdirs();
                    try (OutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
    }
}
