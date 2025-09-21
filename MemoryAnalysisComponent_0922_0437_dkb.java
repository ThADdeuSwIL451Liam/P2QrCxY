// 代码生成时间: 2025-09-22 04:37:30
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.scheduling.annotation.Scheduled;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

@Component
public class MemoryAnalysisComponent implements HealthIndicator {

    private final MemoryMXBean memoryMXBean;

    @Autowired
    public MemoryAnalysisComponent() {
        this.memoryMXBean = ManagementFactory.getMemoryMXBean();
    }

    // 定期执行内存使用情况分析
    @Scheduled(fixedRate = 5000) // 每5秒执行一次
    public void analyzeMemoryUsage() {
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        System.out.println("Heap Memory Usage: " +
                "Initiated = " + heapMemoryUsage.getInit() +
                ", Used = " + heapMemoryUsage.getUsed() +
                ", Committed = " + heapMemoryUsage.getCommitted() +
                ", Max = " + heapMemoryUsage.getMax());

        System.out.println("Non-Heap Memory Usage: " +
                "Initiated = " + nonHeapMemoryUsage.getInit() +
                ", Used = " + nonHeapMemoryUsage.getUsed() +
                ", Committed = " + nonHeapMemoryUsage.getCommitted() +
                ", Max = " + nonHeapMemoryUsage.getMax());
    }

    // 实现HealthIndicator接口的方法，返回内存使用情况的健康状态
    @Override
    public Health health() {
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        if (heapMemoryUsage.getUsed() > heapMemoryUsage.getMax()) {
            // 如果已使用内存超过最大内存，则返回DOWN状态
            return Health.down().withDetail("Heap Memory", "Used memory exceeds max memory").build();
        } else {
            // 否则返回UP状态
            return Health.up().withDetail("Heap Memory", "Used: " + heapMemoryUsage.getUsed() + ", Max: " + heapMemoryUsage.getMax()).build();
        }
    }
}
