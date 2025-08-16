// 代码生成时间: 2025-08-16 23:10:15
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
public class ScheduledTaskExecutor {

    // 每5秒执行一次的任务
    @Scheduled(fixedRate = 5000)
    public void executeFixedRateTask() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            // 业务逻辑
            performTask();
        } catch (Exception e) {
            // 错误处理
            System.err.println("Error in fixed rate task: " + e.getMessage());
        } finally {
            stopWatch.stop();
            System.out.println("Fixed Rate Task Execution Time: " + stopWatch.getTotalTimeMillis() + " ms");
        }
    }

    // 每5秒延迟1秒后执行一次的任务
    @Scheduled(fixedDelay = 6000)
    public void executeFixedDelayTask() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            // 业务逻辑
            performTask();
        } catch (Exception e) {
            // 错误处理
            System.err.println("Error in fixed delay task: " + e.getMessage());
        } finally {
            stopWatch.stop();
            System.out.println("Fixed Delay Task Execution Time: " + stopWatch.getTotalTimeMillis() + " ms");
        }
    }

    // 初始延时1秒，之后每5秒执行一次的任务
    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    public void executeInitialDelayTask() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            // 业务逻辑
            performTask();
        } catch (Exception e) {
            // 错误处理
            System.err.println("Error in initial delay task: " + e.getMessage());
        } finally {
            stopWatch.stop();
            System.out.println("Initial Delay Task Execution Time: " + stopWatch.getTotalTimeMillis() + " ms");
        }
    }

    // 业务逻辑方法
    private void performTask() {
        // 模拟业务操作
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException("Task interrupted", e);
        }
    }
}
