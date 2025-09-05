// 代码生成时间: 2025-09-05 23:57:38
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

/**
 * 错误日志收集器组件
 * 用于捕获并记录全局异常，同时提供错误日志信息
 */
@ControllerAdvice
@Component
public class ErrorLoggerComponent {

    private static final Logger logger = LoggerFactory.getLogger(ErrorLoggerComponent.class);

    /**<ol>
     * 异常处理方法
     * @param e 异常对象
     * @return 错误信息响应实体
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // 记录错误日志
        logger.error("Exception occurred: ", e);
        
        // 返回错误信息，可根据需要自定义错误信息格式
        return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
    }
}
