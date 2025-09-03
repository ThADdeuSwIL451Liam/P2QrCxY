// 代码生成时间: 2025-09-04 07:31:31
package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class SortAlgorithmService {

    public <T extends Comparable<T>> List<T> sortList(List<T> list) {
        try {
            Collections.sort(list);
            return list;
        } catch (Exception e) {
            // Handle the exception in a way that is appropriate for your application
            // This could be logging the error, rethrowing a custom exception, etc.
            throw new RuntimeException("Error occurred during sorting", e);
        }
    }
}
