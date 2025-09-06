// 代码生成时间: 2025-09-07 05:43:08
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

// 订单处理服务
@Service
@Transactional
public class OrderProcessingComponent {

    // 自动注入订单存储仓库
    @Autowired
    private OrderRepository orderRepository;

    // 处理订单的方法
    @PostMapping("/orders")
    public ResponseEntity<Order> processOrder(@RequestBody Order order) {
        // 保存订单
        Order savedOrder = orderRepository.save(order);
        // 返回成功响应
        return ResponseEntity.ok(savedOrder);
    }

    // 处理查询订单的方法
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        // 如果订单存在，则返回订单
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            // 如果订单不存在，返回404错误
            return ResponseEntity.notFound().build();
        }
    }

    // 异常处理
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 自定义异常类
    public class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String message) {
            super(message);
        }
    }
}
