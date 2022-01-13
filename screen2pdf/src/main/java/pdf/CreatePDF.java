package pdf;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import util.ImageUtil;

public class CreatePDF {

    public CreatePDF(ArrayList<BufferedImage> imageList, String filePath) {
        try {
            // 空のドキュメントオブジェクトを作成します
            PDDocument document = new PDDocument();
            int page = 1;
            for (BufferedImage img : imageList) {
                drawImage(document, img, page++);
            }
            document.save(filePath);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawImage(PDDocument document, BufferedImage img, int pageNum) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        page.setRotation(90);
        contentStream.transform(new Matrix(0, 1, -1, 0, page.getMediaBox().getWidth(), 0));
        Rectangle drawRect = new Rectangle();

        if (img.getWidth() > img.getHeight()) { // 横長
            drawRect.width = (int) (page.getMediaBox().getHeight() * 0.8);
            double ratio = (double) drawRect.width / (double) img.getWidth();
            drawRect.height = (int) (img.getHeight() * ratio);
            drawRect.x = (int) (page.getMediaBox().getHeight() * 0.1);
            drawRect.y = (int) ((double) page.getMediaBox().getWidth() / 2.0 - (double) drawRect.height / 2.0);
        } else {
            drawRect.height = (int) (page.getMediaBox().getWidth() * 0.8);
            double ratio = (double) img.getHeight() / (double) drawRect.height;
            drawRect.width = (int) (img.getWidth() * ratio);
            drawRect.y = (int) (page.getMediaBox().getWidth() * 0.1);
            drawRect.x = (int) ((double) page.getMediaBox().getHeight() / 2.0 - (double) drawRect.width / 2.0);
        }

        PDImageXObject image = LosslessFactory.createFromImage(document, img);
        contentStream.drawImage(image, drawRect.x, drawRect.y, drawRect.width, drawRect.height);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(50, 20);
        contentStream.showText("page:" + pageNum);
        contentStream.endText();
        contentStream.close();
    }
}