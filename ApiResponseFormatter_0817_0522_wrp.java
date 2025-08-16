// 代码生成时间: 2025-08-17 05:22:39
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ApiResponseFormatter implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 支持所有响应
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                   Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                   ServerHttpRequest request, ServerHttpResponse response) {
        // 包装原始响应体，加入格式化信息
        if (body instanceof ApiError) {
            ApiError apiError = (ApiError) body;
            response.setStatusCode(apiError.getStatus());
            return apiError;
        }
        // 正常情况下，返回原始响应体
        return new ApiResponse<>(body);
    }
}

// ApiResponse类，用于包装API响应体
class ApiResponse<T> {
    private T data;
    private String message;
    private int code;

    public ApiResponse(T data) {
        this.data = data;
        this.message = "Success";
        this.code = HttpServletResponse.SC_OK;
    }

    // getters and setters
}

// ApiError类，用于处理错误响应
class ApiError {
    private String message;
    private int status;

    public ApiError(String message, int status) {
        this.message = message;
        this.status = status;
    }

    // getters and setters
}