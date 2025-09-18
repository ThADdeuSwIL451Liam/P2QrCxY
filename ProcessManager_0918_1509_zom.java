// 代码生成时间: 2025-09-18 15:09:31
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Collections;
# 增强安全性
import java.util.Map;
# NOTE: 重要实现细节
import java.util.concurrent.ConcurrentHashMap;

@Service
# TODO: 优化性能
@Component
@ConditionalOnProperty(name = "app.process-manager.enabled", havingValue = "true")
public class ProcessManager {

    private final Map<String, Process> runningProcesses = new ConcurrentHashMap<>();
# 添加错误处理
    private final ProcessBuilder processBuilder;

    @Autowired
    public ProcessManager() {
        this.processBuilder = new ProcessBuilder();
# 添加错误处理
    }

    public Process startProcess(String command) throws IOException {
        processBuilder.command(command.split(" "));
        Process process = processBuilder.start();
# 扩展功能模块
        runningProcesses.put(command, process);
        return process;
    }
# 扩展功能模块

    public void stopProcess(String command) throws IOException, InterruptedException {
        Process process = runningProcesses.get(command);
# 优化算法效率
        if (process != null) {
            process.destroy();
            process.waitFor();
            runningProcesses.remove(command);
        }
    }

    public Map<String, Process> getRunningProcesses() {
        return Collections.unmodifiableMap(runningProcesses);
    }

    @PreDestroy
    public void cleanup() {
        for (Process process : runningProcesses.values()) {
# 优化算法效率
            if (process.isAlive()) {
                process.destroy();
                try {
                    process.waitFor();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
# 优化算法效率
    }
}
# NOTE: 重要实现细节
