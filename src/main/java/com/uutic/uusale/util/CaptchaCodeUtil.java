package com.uutic.uusale.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaCodeUtil {

    public static class CaptchaCodeModel {
        private String captchaCode; // Verify code String form
        private BufferedImage captchaImage; // Picture form of verification code

        public String getCaptchaCode() {
            return captchaCode;
        }

        public void setCaptchaCode(String captchaCode) {
            this.captchaCode = captchaCode;
        }

        public BufferedImage getCaptchaImage() {
            return captchaImage;
        }

        public void setCaptchaImage(BufferedImage captchaImage) {
            this.captchaImage = captchaImage;
        }
    }

    private static final int width = 95; // Defining pictures width
    private static final int height = 40; // Defining pictures height
    private static final int codeCount = 4; // Defines the number of authentication codes displayed on the image
    private static final int barLineCount = 40;
    private static final int barLineLength = 12;
    private static final int codeX = 16;
    private static final int codeY = 15;
    private static final String fontName = "Fixedsys";
    private static final int fontHeight = 20;
    private static final Color imageColor = new Color(230, 230, 230);
    private static final Color borderColor = Color.BLACK;
    private static final Color barLineColor = Color.BLACK;
    private static final char[] codeSequence = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static CaptchaCodeModel getCode() {
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics gd = buffImg.getGraphics();
        Random random = new Random();

        // Fill the image with color
        gd.setColor(imageColor);
        gd.fillRect(0, 0, width, height);
        // Create fonts, The size of the font should be determined according to the height of the picture
        gd.setFont(new Font(fontName, Font.BOLD, fontHeight));
        // Draw border with color
        gd.setColor(borderColor);
        gd.drawRect(0, 0, width - 1, height - 1);
        //  Randomly generate interference bar line, The authentication code in the image is not easily detected by other programs
        gd.setColor(barLineColor);
        for (int i = 0; i < barLineCount; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(barLineLength);
            int yl = random.nextInt(barLineLength);
            gd.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode Used to save randomly generated proof codes, So that the user logs in to verify it
        StringBuilder randomCode = new StringBuilder();
        int red, green, blue;
        // Randomly generated codeCount Digital verification code
        for (int i = 0; i < codeCount; i++) {
            //  Get the random number of the validation code
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            //  Generates random color components to construct color values ， The color values of each output of this output will vary
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            //  Draws the validation code into the image with randomly generated colors 。
            gd.setColor(new Color(red, green, blue).darker());
            gd.drawString(code, (i + 1) * codeX, random.nextInt(codeY) + fontHeight);
            //  Combine four random numbers that will be generated 。
            randomCode.append(code);
        }

        CaptchaCodeModel captchaCodeModel = new CaptchaCodeModel();
        captchaCodeModel.setCaptchaCode(randomCode.toString());
        captchaCodeModel.setCaptchaImage(buffImg);

        return captchaCodeModel;
    }
}
