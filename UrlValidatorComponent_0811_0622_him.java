// 代码生成时间: 2025-08-11 06:22:03
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import java.net.URL;

@Component
public class UrlValidatorComponent {

    private final RestTemplate restTemplate;

    public UrlValidatorComponent(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 验证URL链接是否有效
     *
     * @param url 需要验证的URL
     * @return true 如果URL有效，false 如果URL无效
     * @throws RestClientException 如果发生网络错误
     */
    public boolean validateUrl(String url) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (RestClientException e) {
            // 处理RestClientException异常
            return false;
        }
    }
}
