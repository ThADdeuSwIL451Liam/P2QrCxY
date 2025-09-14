// 代码生成时间: 2025-09-14 18:58:41
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@EnableConfigurationProperties(FolderOrganizerProperties.class)
@ConditionalOnProperty(prefix = "folder.organizer", name = "enabled", havingValue = "true")
public class FolderOrganizer {

    private static final Logger logger = LoggerFactory.getLogger(FolderOrganizer.class);

    @Value("\${folder.organizer.source-dir}")
    private String sourceDirectory;

    @Value("\${folder.organizer.target-dir}")
    private String targetDirectory;

    // 构造函数
    public FolderOrganizer(String sourceDirectory, String targetDirectory) {
        this.sourceDirectory = sourceDirectory;
        this.targetDirectory = targetDirectory;
    }

    // 整理文件夹结构
    public void organize() throws IOException {
        try {
            File source = new File(sourceDirectory);
            File target = new File(targetDirectory);

            // 确保源文件夹存在
            if (!source.exists() || !source.isDirectory()) {
                throw new IllegalArgumentException("Source directory does not exist or is not a directory: " + sourceDirectory);
            }

            // 确保目标文件夹存在
            if (!target.exists() {
                target.mkdirs();
            }

            // 遍历源文件夹并移动文件到目标文件夹
            File[] files = source.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    Path sourcePath = Paths.get(file.getAbsolutePath());
                    Path targetPath = Paths.get(targetDirectory, file.getName());
                    Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    logger.info("Moved file {} to {}", file.getAbsolutePath(), targetDirectory);
                }
            }
        } catch (IOException e) {
            logger.error("Error organizing folders", e);
            throw e;
        }
    }
}
