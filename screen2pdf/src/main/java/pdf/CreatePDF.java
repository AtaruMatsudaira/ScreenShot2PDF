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

public class CreatePDF {
    private static CreatePDF _instance = new CreatePDF();

    public static CreatePDF getInstance() {
        return _instance;
    }

    private CreatePDF() {
    }

    public void exportToPDF(ArrayList<BufferedImage> imageList, String filePath) {
        try {
            // 空のドキュメントオブジェクトを作成します
            PDDocument document = new PDDocument();

            for (BufferedImage img : imageList) {
                PDPage page = new PDPage();
                page.setRotation(90);
                document.addPage(page);
                PDImageXObject image = LosslessFactory.createFromImage(document, img);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.transform(new Matrix(0, 1, -1, 0, page.getMediaBox().getWidth(), 0));
                contentStream.drawImage(image, 0, 0, page.getMediaBox().getHeight(), page.getMediaBox().getWidth());

                contentStream.close();
            }
            document.save(filePath);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
