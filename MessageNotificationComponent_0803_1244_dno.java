// 代码生成时间: 2025-08-03 12:44:40
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ExecutionException;

/**
 * 消息通知系统的Spring Boot组件
 */
@Component
public class MessageNotificationComponent {

    private static final Logger logger = LoggerFactory.getLogger(MessageNotificationComponent.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 发送消息到Kafka主题
     *
     * @param topic Kafka主题名称
     * @param message 要发送的消息
     */
    public void sendMessage(String topic, String message) {
        try {
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    logger.info("Message sent successfully to topic {}", topic);
                }

                @Override
                public void onFailure(Throwable ex) {
                    logger.error("Error sending message to topic {}", topic, ex);
                }
            });
        } catch (Exception e) {
            logger.error("Exception while sending message to Kafka", e);
        }
    }

    /**
     * 错误处理方法
     *
     * @param ex 异常信息
     * @param message 失败的消息内容
     */
    public void handleError(Exception ex, String message) {
        logger.error("Error occurred while handling message: {}
Error: {}", message, ex.getMessage());
    }
}
