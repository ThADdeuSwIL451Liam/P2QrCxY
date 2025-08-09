// 代码生成时间: 2025-08-09 16:33:02
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/sort")
@Service
public class SortingService {

    private static final String SORT_ERROR_MSG = "Error occurred while sorting the list";

    // This method sorts a list of integers using the bubble sort algorithm.
    @GetMapping("/bubble")
    public ResponseEntity<List<Integer>> bubbleSort(@RequestParam List<Integer> numbers) {
        try {
            if (numbers == null || numbers.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            Collections.sort(numbers);
            return ResponseEntity.ok(numbers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    // This method sorts a list of integers using the quick sort algorithm.
    @GetMapping("/quick")
    public ResponseEntity<List<Integer>> quickSort(@RequestParam List<Integer> numbers) {
        try {
            if (numbers == null || numbers.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            List<Integer> sortedList = quickSortHelper(numbers, 0, numbers.size() - 1);
            return ResponseEntity.ok(sortedList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    private List<Integer> quickSortHelper(List<Integer> numbers, int low, int high) {
        if (low < high) {
            int pi = partition(numbers, low, high);
            quickSortHelper(numbers, low, pi - 1);
            quickSortHelper(numbers, pi + 1, high);
        }
        return numbers;
    }

    private int partition(List<Integer> numbers, int low, int high) {
        int pivot = numbers.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (numbers.get(j) < pivot) {
                i++;
                Collections.swap(numbers, i, j);
            }
        }
        Collections.swap(numbers, i + 1, high);
        return i + 1;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception ex) {
        return ResponseEntity.status(500).body(SORT_ERROR_MSG + ": " + ex.getMessage());
    }
}

@Configuration
public class SortingConfig {

    // Conditional bean for sorting service.
    @Bean
    @ConditionalOnMissingBean
    public SortingService sortingService() {
        return new SortingService();
    }
}
