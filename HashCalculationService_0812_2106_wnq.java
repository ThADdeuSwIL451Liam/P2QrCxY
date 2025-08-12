// 代码生成时间: 2025-08-12 21:06:58
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;

@Service
public class HashCalculationService {

    private MessageDigest messageDigest;

    @Autowired
    private HashConfiguration hashConfiguration;

    @PostConstruct
    private void init() {
        try {
            messageDigest = MessageDigest.getInstance(hashConfiguration.getAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to initialize hash algorithm.", e);
        }
    }

    /**
     * Calculate the hash value for the given input string.
     * 
     * @param input The input string to calculate the hash for.
     * @return The hash value as a hexadecimal string.
     */
    public String calculateHash(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty.");
        }
        return bytesToHex(messageDigest.digest(input.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Convert a byte array to a hexadecimal string.
     * 
     * @param bytes The byte array to convert.
     * @return The hexadecimal string representation of the byte array.
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

/**
 * HashConfiguration is a Spring Boot configuration class for hash calculation settings.
 */
@Configuration
@ConditionalOnMissingBean(HashConfiguration.class)
public class HashConfiguration {

    private String algorithm = "SHA-256";

    // This method can be overridden with a different algorithm if needed.
    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
