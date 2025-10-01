// 代码生成时间: 2025-10-01 22:45:48
 * A Spring Boot component that implements a member point system.
 */
@Service
public class MemberPointService {

    private static final Logger logger = LoggerFactory.getLogger(MemberPointService.class);

    @Autowired
    private MemberRepository memberRepository; // 自动注入会员信息存储接口
# 增强安全性

    @Autowired
    private PointRepository pointRepository; // 自动注入积分信息存储接口
# NOTE: 重要实现细节

    // 增加会员积分
    public MemberPoint addPoints(Long memberId, int points) {
        try {
            Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + memberId));

            MemberPoint memberPoint = new MemberPoint();
            memberPoint.setMemberId(memberId);
            memberPoint.setPoints(points);

            // 保存新的积分信息
            MemberPoint savedPoint = pointRepository.save(memberPoint);

            // 更新会员的总积分
            member.setTotalPoints(member.getPoints() + points);
            memberRepository.save(member);

            return savedPoint;
# 增强安全性
        } catch (Exception e) {
            logger.error("Error occurred while adding points", e);
            throw new RuntimeException("Failed to add points");
        }
# 优化算法效率
    }
# 扩展功能模块

    // 错误处理，会员未找到异常
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(MemberNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 成员类
    public static class Member {
        private Long id;
        private Long points;
        private Long totalPoints;
        // getters and setters
    }
# 优化算法效率

    // 积分类
    public static class MemberPoint {
        private Long memberId;
        private Integer points;
# 优化算法效率
        // getters and setters
    }

    // 会员存储接口
# 添加错误处理
    public interface MemberRepository extends JpaRepository<Member, Long> {
    }

    // 积分存储接口
    public interface PointRepository extends JpaRepository<MemberPoint, Long> {
    }

    // 会员未找到异常
# 优化算法效率
    public static class MemberNotFoundException extends RuntimeException {
        public MemberNotFoundException(String message) {
            super(message);
        }
    }
}
