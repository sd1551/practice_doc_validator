package org.practice_parser.handler;

import org.practice_parser.dto.DocumentDto;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.sourceforge.tess4j.util.ImageHelper.rotateImage;

@Service
public class PassportParserHandlerImpl implements DocumentParserHandler {

    @Override
    public DocumentDto parseDocument(String filePath) {
        File imageFile = new File(filePath);
        ITesseract instance = new Tesseract();

        instance.setDatapath("tessdata");
        instance.setLanguage("rus");

        try {
            BufferedImage img = ImageIO.read(imageFile);
            if (img == null) {
                throw new IOException("Изображение не может быть прочитано!");
            }

            BufferedImage enhancedImage = enhanceImage(img);

            BufferedImage croppedImage = enhancedImage.getSubimage(100, enhancedImage.getHeight() / 2,
                    enhancedImage.getWidth() - 100, enhancedImage.getHeight() / 2);
            String result = instance.doOCR(croppedImage);

            BufferedImage rotatedEnhancedImage = rotateImage(enhancedImage, -90);

            instance.setOcrEngineMode(1);
            String result1 = instance.doOCR(rotatedEnhancedImage);

            String series = extractSeries(result1);
            String number = extractNumber(result1);
            String fullName = extractFullName(result);

            String[] nameParts = fullName.split(" ");
            String lastName = nameParts.length > 0 ? nameParts[0] : "";
            String firstName = nameParts.length > 1 ? nameParts[1] : "";
            String middleName = nameParts.length > 2 ? nameParts[2] : "";

            return new DocumentDto(firstName, lastName, middleName, series + " " + number,
                    DocumentDto.DocumentType.PASSPORT);
        } catch (TesseractException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private BufferedImage enhanceImage(BufferedImage img) {
        BufferedImage highResImage = scaleImage(img, 2);

        BufferedImage contrastEnhanced = enhanceContrast(highResImage);
        BufferedImage sharpened = sharpenImage(contrastEnhanced);

        BufferedImage denoisedImage = denoiseImage(sharpened);

        return denoisedImage;
    }

    private BufferedImage scaleImage(BufferedImage img, double scaleFactor) {
        int scaledWidth = (int) (img.getWidth() * scaleFactor);
        int scaledHeight = (int) (img.getHeight() * scaleFactor);

        BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.drawImage(img, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        return scaledImage;
    }

    private BufferedImage enhanceContrast(BufferedImage img) {
        BufferedImage contrastImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = contrastImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        float scaleFactor = 1.3f;
        float offset = 10f;
        RescaleOp rescaleOp = new RescaleOp(scaleFactor, offset, null);
        g2d.drawImage(contrastImage, rescaleOp, 0, 0);

        return contrastImage;
    }

    private BufferedImage sharpenImage(BufferedImage img) {
        float[] sharpenMatrix = {0, -1, 0, -1, 5, -1, 0, -1, 0};
        BufferedImageOp sharpen = new ConvolveOp(new Kernel(3, 3, sharpenMatrix), ConvolveOp.EDGE_NO_OP, null);
        return sharpen.filter(img, null);
    }

    private BufferedImage denoiseImage(BufferedImage img) {
        float[] matrix = new float[9];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = 1.0f / 9.0f;
        }
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, matrix));
        return op.filter(img, null);
    }

    private String extractSeries(String ocrText) {
        Pattern pattern = Pattern.compile("\\b(\\d{2}\\s*\\d{2})");
        Matcher matcher = pattern.matcher(ocrText);
        if (matcher.find()) {
            return matcher.group(1).replaceAll("\\s+", " ");
        }
        return "Серия не найдена";
    }

    private String extractNumber(String ocrText) {
        Pattern pattern = Pattern.compile("\\b(\\d{6})");
        Matcher matcher = pattern.matcher(ocrText);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "Номер не найден";
    }

    private String extractFullName(String ocrText) {
        Pattern pattern = Pattern.compile("(?U)\\b[А-ЯЁ]{4,}\\b");
        Matcher matcher = pattern.matcher(ocrText);

        int count = 0;
        StringBuilder fullName = new StringBuilder();

        while (matcher.find()) {
            String word = matcher.group().trim();
            if (word.matches("[А-ЯЁ]+")) {
                fullName.append(word).append(" ");
                count++;
                if (count == 3) {
                    break;
                }
            }
        }

        return fullName.toString().trim();
    }
}