package pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class CreatePDF {
    private static CreatePDF _instance = new CreatePDF();

    public static CreatePDF getInstance() {
        return _instance;
    }

    private CreatePDF() {
    }

    public void unko() {
        try {
            PDDocument document = new PDDocument();
            document.getPages().iterator();
            PDPage page = new PDPage();

            PDFont font = PDType1Font.COURIER;
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(font, 12);
            int posX = (int) (page.getArtBox().getWidth() * 0.8);
            int posY = (int) (page.getArtBox().getHeight() * 0.2);
            contentStream.newLineAtOffset(posX, posY);
            contentStream.showText("Hello World");

            contentStream.endText();
            contentStream.close();
            document.addPage(page);
            document.save("helloworld__.pdf");
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
