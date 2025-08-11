// 代码生成时间: 2025-08-12 07:40:28
// PerformanceMonitor.java
// 系统性能监控工具组件
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PerformanceMonitor implements HealthIndicator {

    private final AtomicLong lastSystemCpuLoad = new AtomicLong(0);
    private final AtomicLong lastProcessCpuLoad = new AtomicLong(0);
    private final OperatingSystemMXBean operatingSystemMXBean;

    public PerformanceMonitor() {
        this.operatingSystemMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    }

    @Override
    public Health health() {
        try {
            long systemCpuLoad = operatingSystemMXBean.getSystemCpuLoad() * 100;
            long processCpuLoad = operatingSystemMXBean.getProcessCpuLoad() * 100;
            if (systemCpuLoad < 90 && processCpuLoad < 90) {
                return Health.up().withDetail("SystemCpuLoad", systemCpuLoad).withDetail("ProcessCpuLoad", processCpuLoad).build();
            } else {
                return Health.down().withDetail("SystemCpuLoad", systemCpuLoad).withDetail("ProcessCpuLoad", processCpuLoad).build();
            }
        } catch (Exception e) {
            // 错误处理，返回Health.status为UNKNOWN时，表示系统性能监控工具无法确定健康状态
            return Health.unknown().withDetail("Exception", e.getMessage()).build();
        }

    // 用于获取系统CPU负载的方法
    public long getSystemCpuLoad() {
        return operatingSystemMXBean.getSystemCpuLoad() * 100;
    }

    // 用于获取进程CPU负载的方法
    public long getProcessCpuLoad() {
        return operatingSystemMXBean.getProcessCpuLoad() * 100;
    }
}
