package util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {
    public static Image getScaledImage(Image srcImg, int w, int h) throws Exception {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    public static int getScreenDisplayWidth(BufferedImage image) {
        double ratio = (double) image.getHeight() / (double) image.getWidth();
        double width = (double) image.getHeight() / ratio;
        return (int) width;
    }
}
