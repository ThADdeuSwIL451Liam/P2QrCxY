// 代码生成时间: 2025-08-31 15:43:52
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class BackupAndRestoreComponent {

    @Value("{backup.location}")
    private String backupLocation;

    private final ResourceLoader resourceLoader;

    public BackupAndRestoreComponent(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * Backups the specified data to a backup location.
     * 
     * @param dataResourcePath The path to the data to be backed up.
     * @throws IOException If an I/O error occurs during backup.
     */
    public void backupData(String dataResourcePath) throws IOException {
        Resource resource = resourceLoader.getResource(dataResourcePath);
        Assert.notNull(resource, "Data resource cannot be null.");
        Assert.isTrue(resource.exists(), "Data resource does not exist.");

        Path backupPath = Paths.get(backupLocation, resource.getFilename());
        Files.copy(resource.getInputStream(), backupPath, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Restores the specified data from the backup location.
     * 
     * @param backupResourcePath The path to the backup resource to be restored.
     * @param targetPath The target path where the data will be restored.
     * @throws IOException If an I/O error occurs during restore.
     */
    public void restoreData(String backupResourcePath, String targetPath) throws IOException {
        Resource backupResource = resourceLoader.getResource(backupResourcePath);
        Assert.notNull(backupResource, "Backup resource cannot be null.");
        Assert.isTrue(backupResource.exists(), "Backup resource does not exist.");

        Path targetPathObj = Paths.get(targetPath);
        Files.copy(backupResource.getInputStream(), targetPathObj, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Sets the backup location.
     * 
     * @param backupLocation The backup location to set.
     */
    public void setBackupLocation(String backupLocation) {
        this.backupLocation = backupLocation;
    }

    /**
     * Gets the backup location.
     * 
     * @return The backup location.
     */
    public String getBackupLocation() {
        return backupLocation;
    }
}
