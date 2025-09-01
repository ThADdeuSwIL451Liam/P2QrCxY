// 代码生成时间: 2025-09-01 10:28:02
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

public class ApiResponseUtil {
    
    // Method to create a standard success response
    public static Map<String, Object> successResponse(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", data);
        return response;
    }

    // Method to create a standard error response
    public static Map<String, Object> errorResponse(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", message);
        response.put("statusCode", status.value());
        return response;
    }

    // Method to create a standard error response with a custom status code
    public static Map<String, Object> errorResponse(int statusCode, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", message);
        response.put("statusCode", statusCode);
        return response;
    }

    // Method to create a standard error response with a custom status code and error code
    public static Map<String, Object> errorResponse(int statusCode, String message, String errorCode) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", message);
        response.put("statusCode", statusCode);
        response.put("errorCode", errorCode);
        return response;
    }
}
