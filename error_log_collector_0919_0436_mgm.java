// 代码生成时间: 2025-09-19 04:36:23
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.context.request.WebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.io.PrintWriter;
import java.io.StringWriter;

@SpringBootApplication
public class ErrorLogCollector {
    
    private static final Logger logger = LoggerFactory.getLogger(ErrorLogCollector.class);
    
    public static void main(String[] args) {
        SpringApplication.run(ErrorLogCollector.class, args);
    }
    
    @Bean
    public HandlerExceptionResolver handlerExceptionResolver() {
        return (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) -> {
            logger.error("Exception occurred: " + ex.getMessage(), ex);
            
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String stackTrace = sw.toString();
                
                // Log error details
                logger.error("Request URI : " + request.getRequestURI());
                logger.error("Timestamp : " + new Date());
                logger.error("Exception StackTrace : " + stackTrace);
            } catch (Exception e) {
                logger.error("Error logging exception: ", e);
            }
            
            return new ModelAndView("error", "exception", ex);
        };
    }
}