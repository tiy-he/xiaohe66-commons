package com.xiaohe66.common.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaohe
 * @time 2021.07.22 09:36
 */
public class ImageCanvas {

    private final BufferedImage fullImage;
    private final Graphics2D fullImageGraphics;

    public ImageCanvas(File background) throws IOException {
        fullImage = ImageIO.read(background);
        fullImageGraphics = fullImage.createGraphics();
    }

    public ImageCanvas(InputStream background) throws IOException {
        fullImage = ImageIO.read(background);
        fullImageGraphics = fullImage.createGraphics();
    }

    private BufferedImage copyImage(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage newImage = fullImageGraphics.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);

        Graphics2D newImageGraphics = newImage.createGraphics();
        newImageGraphics.drawImage(image, 0, 0, null);

        newImageGraphics.dispose();

        return newImage;
    }

    private BufferedImage drawSide(BufferedImage sourceImage, ImageDrawConfig.Side side) {

        BufferedImage bg = copyImage(sourceImage);

        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();

        Graphics2D bgGraphics = bg.createGraphics();
        bgGraphics.setColor(side.getColor());
        bgGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int sideSize = side.getSize();
        int before = sideSize / 2;

        for (int h = 0; h < height; h++) {

            for (int w = 0; w < width; w++) {

                int rgb = sourceImage.getRGB(w, h);

                if (rgb != 0) {
                    // todo : 耗时操作
                    bgGraphics.fillOval(w - before, h - before, sideSize, sideSize);
                }
            }
        }
        bgGraphics.drawImage(sourceImage, 0, 0, null);
        bgGraphics.dispose();

        return bg;
    }

    private BufferedImage createFontImage(String str, ImageDrawConfig config) {

        Font font = config.getFont();
        int fontSize = font.getSize();
        ImageDrawConfig.Side side = config.getSide();
        int sideSize = side != null ? side.getSize() : 0;

        Rectangle2D r = font.getStringBounds(str, new FontRenderContext(
                AffineTransform.getScaleInstance(1, 1), false, false));

        int height = (int) Math.round(r.getHeight());
        int width = (int) Math.round(r.getWidth() + sideSize * 1.5);

        BufferedImage image = fullImageGraphics.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);

        Graphics2D fontGraphics = image.createGraphics();

        fontGraphics.setFont(font);
        fontGraphics.setColor(config.getFontColor());
        fontGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        fontGraphics.drawString(str, sideSize / 2, fontSize);

        fontGraphics.dispose();

        BufferedImage bg = side == null ? image : drawSide(image, side);

        int minH = height;
        int maxH = 0;
        int minW = width;
        int maxW = 0;
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                int rgb = bg.getRGB(w, h);
                if (rgb != 0) {
                    minH = Math.min(minH, h);
                    maxH = Math.max(maxH, h);

                    minW = Math.min(minW, w);
                    maxW = Math.max(maxW, w);
                }
            }
        }
        minW -= 2;
        minH -= 2;
        maxH += 2;
        maxW += 2;

        BufferedImage ret = fullImageGraphics.getDeviceConfiguration()
                .createCompatibleImage(maxW - minW, maxH - minH, Transparency.TRANSLUCENT);

        Graphics2D retGraphics = ret.createGraphics();
        retGraphics.drawImage(bg, -minW, -minH, null);

        retGraphics.dispose();

        return ret;
    }

    private void drawToFullImage(BufferedImage image, int x, int y, int rotate) {
        drawToFullImage(image, x, y, rotate, true);
    }

    private void drawToFullImage(BufferedImage image, int x, int y, int rotate, boolean isCenter) {

        int w = isCenter ? x - image.getWidth() / 2 : x;
        int h = y - image.getHeight() / 2;

        Graphics2D graphics = fullImage.createGraphics();
        graphics.rotate(Math.toRadians(rotate));
        graphics.drawImage(image, w, h, null);
        graphics.dispose();
    }

    private void drawOneFont(ImageFont imageFont, boolean isCenter) throws IOException {

        ImageFontText fontText = imageFont.getFontTextList().get(0);

        ImageDrawConfig config = fontText.getConfig();
        BufferedImage image = createFontImage(fontText.getText(), config);

        drawToFullImage(image, imageFont.getX(), imageFont.getY(), imageFont.getRotate(), isCenter);
    }

    private void drawMergeFont(ImageFont imageFont) throws IOException {

        List<ImageFontText> textList = imageFont.getFontTextList();

        List<BufferedImage> imageList = textList.stream()
                .map(fontText -> createFontImage(fontText.getText(), fontText.getConfig()))
                .collect(Collectors.toList());

        int width = 0;
        int height = 0;
        for (int i = 0; i < imageList.size(); i++) {

            BufferedImage image = imageList.get(i);

            ImageFontText fontText = textList.get(i);

            width += image.getWidth() + fontText.getConfig().getOffset();
            height = Math.max(height, image.getHeight());
        }

        BufferedImage margeImage = fullImageGraphics.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        Graphics2D margeImageGraphics = margeImage.createGraphics();

        int before = 0;
        for (int i = 0; i < imageList.size(); i++) {

            before += textList.get(i).getConfig().getOffset();

            BufferedImage tmpImage = imageList.get(i);

            margeImageGraphics.drawImage(tmpImage, before, height - tmpImage.getHeight(), null);
            before += tmpImage.getWidth();

        }

        margeImageGraphics.dispose();

        drawToFullImage(margeImage, imageFont.getX(), imageFont.getY(), imageFont.getRotate());
    }

    public void drawFont(ImageFont imageFont) throws IOException {
        drawFont(imageFont, true);
    }

    public void drawFont(ImageFont imageFont, boolean isCenter) throws IOException {

        List<ImageFontText> fontTextList = imageFont.getFontTextList();

        if (fontTextList.size() <= 1) {

            drawOneFont(imageFont, isCenter);
        } else {

            drawMergeFont(imageFont);
        }
    }

    public void drawImage(File imageFile, int x, int y, int width, int height) throws IOException {

        BufferedImage image = ImageIO.read(imageFile);
        drawImage(image, x, y, width, height);
    }

    public void drawImage(Image image, int x, int y, int width, int height) {

        x -= width / 2;
        y -= height / 2;

        fullImageGraphics.drawImage(image, x, y, width, height, null);
    }

    public BufferedImage getImage() {
        return fullImage;
    }

    public int getWidth() {
        return fullImage.getWidth();
    }
}
