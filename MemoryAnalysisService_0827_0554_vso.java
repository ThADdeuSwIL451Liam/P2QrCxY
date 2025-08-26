// 代码生成时间: 2025-08-27 05:54:49
package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import java.io.IOException;
import java.lang.management.ManagementFactory;
# 改进用户体验
import java.nio.file.Paths;
import java.nio.file.Files;
# 改进用户体验
import java.nio.file.Path;
# NOTE: 重要实现细节

@Service
public class MemoryAnalysisService {

    private final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
    private final ObjectName memoryMXBeanName = new ObjectName("java.lang:type=Memory");
# 扩展功能模块

    /**
     * Gets the memory usage details.
     *
     * @return Memory usage details as a String.
# 改进用户体验
     * @throws AttributeNotFoundException
     * @throws MBeanException
# 增强安全性
     * @throws ReflectionException
     * @throws InstanceNotFoundException
     */
    public String getMemoryUsageDetails() {
        try {
            // Getting memory usage details from Memory MXBean
            long heapMemoryUsage = (Long) mBeanServer.getAttribute(memoryMXBeanName, "HeapMemoryUsage");
            long nonHeapMemoryUsage = (Long) mBeanServer.getAttribute(memoryMXBeanName, "NonHeapMemoryUsage");

            // Preparing the memory usage details as a String
            return "Heap Memory Usage: " + heapMemoryUsage + " bytes
" +
                    "Non-Heap Memory Usage: " + nonHeapMemoryUsage + " bytes";
        } catch (AttributeNotFoundException | MBeanException | ReflectionException | InstanceNotFoundException e) {
            throw new RuntimeException("Error retrieving memory usage details", e);
        }
    }

    /**
     * Get the size of the application's home directory.
     *
# 扩展功能模块
     * @return The size of the application's home directory.
     * @throws IOException
# 添加错误处理
     */
    public long getApplicationHomeSize() throws IOException {
        ApplicationHome applicationHome = new ApplicationHome();
        Path homePath = Paths.get(applicationHome.getDir().getAbsolutePath());
        long size;

        if (Files.exists(homePath)) {
            try {
                size = Files.walk(homePath)
                        .mapToLong(path -> path.toFile().length())
                        .sum();
# NOTE: 重要实现细节
            } catch (IOException e) {
                throw new IOException("Error calculating the size of the application's home directory", e);
            }
# FIXME: 处理边界情况
        } else {
            throw new IOException("Application home directory does not exist");
        }

        return size;
    }
# 改进用户体验
}
