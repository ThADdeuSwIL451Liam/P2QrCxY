// 代码生成时间: 2025-08-27 22:50:45
// TestReportGenerator.java

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

@Component
public class TestReportGenerator {

    @Value("{report.directory}")
    private String reportDirectory;

    public List<String> generateTestReport(List<String> testResults) {
        // 检查输入列表是否为空
        if (testResults == null || testResults.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Test results list cannot be empty.");
        }

        try {
            // 创建报告目录
            Path reportPath = Paths.get(reportDirectory);
            Files.createDirectories(reportPath);

            // 生成报告文件路径
            Path reportFilePath = reportPath.resolve("test_report.txt");

            // 写入测试结果到报告文件
            List<String> lines = new ArrayList<>();
            for (String result : testResults) {
                lines.add(result);
            }
            Files.write(reportFilePath, lines);

            return lines;
        } catch (IOException e) {
            // 处理文件写入异常
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to generate test report: " + e.getMessage());
        }
    }
}
