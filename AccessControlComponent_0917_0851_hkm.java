// 代码生成时间: 2025-09-17 08:51:23
package com.example.demo.component;

import org.springframework.stereotype.Component;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Component
public class AccessControlComponent {

    public boolean hasPermission() {
        // Fetch the current authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user has the required role
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"));
    }

    @GetMapping("/secure")
    public String secureEndpoint() {
        // Check if the user has permission to access the endpoint
        if (!hasPermission()) {
            throw new AccessDeniedException("Insufficient permissions to access this resource");
        }
        
        return "Access granted to secure endpoint";
    }
}

/**
 * Global exception handler for AccessDeniedException.
 */
@RestControllerAdvice
class AccessDeniedExceptionHandler extends ResponseEntityExceptionHandler {
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        // Log the exception (omitting logging code for brevity)
        // Return a 403 Forbidden response with a custom message
        return new ResponseEntity<>("Access Denied: " + ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
