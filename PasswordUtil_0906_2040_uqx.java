// 代码生成时间: 2025-09-06 20:40:12
import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PasswordUtil {

    private static final String ALGORITHM = "AES";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(256, SECURE_RANDOM);
        return keyGenerator.generateKey();
    }

    public String encrypt(String password, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedPassword, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    // Error handling example
    public String handleEncryptionError(String password, String key) {
        try {
            return encrypt(password, key);
        } catch (Exception e) {
            // Log the error and return a default or error message
            // e.g., e.printStackTrace();
            return "Encryption failed: " + e.getMessage();
        }
    }

    public String handleDecryptionError(String encryptedPassword, String key) {
        try {
            return decrypt(encryptedPassword, key);
        } catch (Exception e) {
            // Log the error and return a default or error message
            // e.g., e.printStackTrace();
            return "Decryption failed: " + e.getMessage();
        }
    }
}
