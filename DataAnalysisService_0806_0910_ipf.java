// 代码生成时间: 2025-08-06 09:10:18
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.math.BigDecimal;

@Service
public class DataAnalysisService {

    private final DataAnalysisRepository dataAnalysisRepository;

    @Autowired
    public DataAnalysisService(DataAnalysisRepository dataAnalysisRepository) {
        this.dataAnalysisRepository = dataAnalysisRepository;
    }

    /**
     * Get statistical data analysis.
     *
     * @return A list of analysis results.
     */
    public List<AnalysisResult> getAnalysisResults() {
        try {
            return dataAnalysisRepository.findAllAnalysisResults();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving analysis results", e);
        }
    }

    /**
     * Calculate average of the data points.
     *
     * @param dataPoints The list of data points.
     * @return The average as a BigDecimal.
     */
    public BigDecimal calculateAverage(List<BigDecimal> dataPoints) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal point : dataPoints) {
            sum = sum.add(point);
        }
        return sum.divide(BigDecimal.valueOf(dataPoints.size()), 2, BigDecimal.ROUND_HALF_UP);
    }
}