// 代码生成时间: 2025-09-30 21:09:49
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class WatermarkService {

    private static final String WATERMARK_TEXT = "Copyright 2023";
    private static final int FONT_SIZE = 48;
    private static final int OPACITY = 128;

    public BufferedImage addWatermark(BufferedImage originalImage) {
        try {
            // 获取原图像的宽高
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();

            // 创建一个同样大小的图像用于添加水印
            BufferedImage watermarkedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = watermarkedImage.createGraphics();

            // 设置透明度
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) OPACITY / 255));
            // 设置颜色和字体
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));

            // 获取字体的高度
            int fontHeight = g2d.getFontMetrics().getHeight();

            // 设置水印位置
            int x = width - g2d.getFontMetrics().stringWidth(WATERMARK_TEXT) - 10;
            int y = height - fontHeight - 10;

            // 绘制水印
            g2d.drawString(WATERMARK_TEXT, x, y);
            g2d.drawImage(originalImage, 0, 0, null);
            g2d.dispose();

            return watermarkedImage;
        } catch (Exception e) {
            // 错误处理
            throw new RuntimeException("Failed to add watermark: " + e.getMessage(), e);
        }
    }

    public void saveImageWithWatermark(BufferedImage image, String path) {
        try {
            File outputFile = new File(path);
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            // 错误处理
            throw new RuntimeException("Failed to save image: " + e.getMessage(), e);
        }
    }
}
