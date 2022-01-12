package pdf;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import util.ImageUtil;

public class CreatePDF {

    public CreatePDF(ArrayList<BufferedImage> imageList, String filePath) {
        try {
            // 空のドキュメントオブジェクトを作成します
            PDDocument document = new PDDocument();

            for (BufferedImage img : imageList) {
                drawImage(document, img);
            }
            document.save(filePath);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawImage(PDDocument document, BufferedImage img) throws IOException {
        PDPage page = new PDPage();
        document.addPage(page);
        PDImageXObject image = LosslessFactory.createFromImage(document, img);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        if(img.getWidth()>img.getHeight()){
            page.setRotation(90);
            contentStream.transform(new Matrix(0, 1, -1, 0, page.getMediaBox().getWidth(), 0));
        
        }
        contentStream.drawImage(image, 0, 0, (float) ImageUtil.getScreenDisplayWidth(img),
                page.getMediaBox().getWidth());

        contentStream.close();
    }
}
