// 代码生成时间: 2025-08-01 20:41:55
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;

@Component
public class JsonDataConverter implements ConverterFactory<String, Map<String, Object>> {

    private final ObjectMapper objectMapper;

    public JsonDataConverter() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public <T> Converter<String, T> getConverter(Class<T> targetType) {
        return source -> {
            try {
                // Assumes the target type is a Map
                return objectMapper.readValue(source, targetType);
            } catch (Exception e) {
                // Handle conversion error
                throw new IllegalArgumentException("Failed to convert JSON to map: " + e.getMessage(), e);
            }
        };
    }

    // Registers the converter with the message converter registry
    public void addConverter(MappingJackson2HttpMessageConverter converter) {
        // Register the converter to handle application/json media type
        converter.setObjectMapper(objectMapper);
        converter.addSupportedMediaType(MediaType.APPLICATION_JSON);
    }

    // Optionally, you can add a method to convert Map to JSON string if needed
    public String convertMapToJson(Map<String, Object> map) throws JsonProcessingException {
        return objectMapper.writeValueAsString(map);
    }
}
