// 代码生成时间: 2025-08-01 03:36:03
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileBackupService {

    // 方法：备份文件
    public String backupFile(String sourcePath, String backupPath) {
        try {
            // 检查源文件是否存在
            if (!Files.exists(Paths.get(sourcePath))) {
                throw new IllegalArgumentException("Source file does not exist.");
            }

            // 复制文件到备份路径
            Files.copy(Paths.get(sourcePath), Paths.get(backupPath), StandardCopyOption.REPLACE_EXISTING);
            return "Backup successful.";
        } catch (IOException e) {
            // 处理IO异常
            return "Error occurred during backup: " + e.getMessage();
        } catch (IllegalArgumentException e) {
            // 处理参数异常
            return e.getMessage();
        }
    }

    // 方法：同步文件
    public String syncFiles(String sourcePath, String targetPath) {
        try {
            // 检查源文件是否存在
            if (!Files.exists(Paths.get(sourcePath))) {
                throw new IllegalArgumentException("Source file does not exist.");
            }

            // 检查目标文件是否存在，如果存在则删除
            if (Files.exists(Paths.get(targetPath))) {
                Files.delete(Paths.get(targetPath));
            }

            // 复制文件到目标路径
            Files.copy(Paths.get(sourcePath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
            return "Sync successful.";
        } catch (IOException e) {
            // 处理IO异常
            return "Error occurred during sync: " + e.getMessage();
        } catch (IllegalArgumentException e) {
            // 处理参数异常
            return e.getMessage();
        }
    }
}
