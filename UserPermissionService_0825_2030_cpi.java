// 代码生成时间: 2025-08-25 20:30:59
package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPermissionService {

    // 模拟的权限验证方法
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminOnlyOperation() {
        return "Admin Operation";
    }

    // 获取用户权限列表
    public List<String> getUserPermissions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getAuthorities().iterator().hasNext()) {
            throw new AccessDeniedException("No permissions found");
        }
        return authentication.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toList());
    }

    // 错误处理方法
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(403).body("Access Denied: " + ex.getMessage());
    }

    // 其他业务方法，例如角色管理等，可以根据实际需求添加
    // ...
}
