package com.xiaohe66.common.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ImageCanvasTest {

    static ImageCanvas canvas;

    static String font = "Microsoft YaHei";

    static {
        try {
            File file = getFile("bg.jpg");
            canvas = new ImageCanvas(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void drawName(int times, String name) throws IOException {

        int nameSize = 38 * times;

        ImageDrawConfig nameConfig = ImageDrawConfig.builder()
                .font(new Font(font, Font.BOLD + Font.ITALIC, nameSize))
                .fontColor(Color.white)
                .side(new ImageDrawConfig.Side(new Color(0x990010), nameSize / 5))
                .build();

        ImageFont nameFont = new ImageFont(830, 630, 355,
                Collections.singletonList(new ImageFontText(name, nameConfig)));

        canvas.drawFont(nameFont);
    }

    private static void drawDesc(int times, int full, int minus) throws IOException {

        int descSize1 = 40 * times;
        int descSize2 = 110 * times;

        ImageDrawConfig descConfig0 = ImageDrawConfig.builder()
                .font(new Font(font, Font.BOLD + Font.ITALIC, descSize1))
                .fontColor(Color.yellow)
                .side(new ImageDrawConfig.Side(Color.black, descSize1 / 5))
                .build();

        ImageDrawConfig descConfig1 = ImageDrawConfig.builder()
                .font(new Font(font, Font.BOLD + Font.ITALIC, descSize1))
                .fontColor(Color.yellow)
                .offset(-35)
                .side(new ImageDrawConfig.Side(Color.black, descSize1 / 5))
                .build();

        ImageDrawConfig descConfig2 = ImageDrawConfig.builder()
                .font(new Font(font, Font.BOLD + Font.ITALIC, descSize2))
                .fontColor(Color.yellow)
                .offset(-15)
                .side(new ImageDrawConfig.Side(Color.black, descSize2 / 5))
                .build();

        List<ImageFontText> descFontTextList = Arrays.asList(
                new ImageFontText("满", descConfig0),
                new ImageFontText(String.valueOf(full), descConfig2),
                new ImageFontText("元减", descConfig1),
                new ImageFontText(String.valueOf(minus), descConfig2),
                new ImageFontText("元", descConfig1)
        );

        ImageFont descFont = new ImageFont(800, 970, 355, descFontTextList);

        canvas.drawFont(descFont);
    }

    private static void drawTime(int times) throws IOException {

        int timeSize = 16 * times;

        ImageDrawConfig timeConfig = ImageDrawConfig.builder()
                .font(new Font(font, Font.BOLD + Font.ITALIC, timeSize))
                .fontColor(Color.white)
                .build();

        ImageFont beginTimeFont = new ImageFont(450, 1215, 355,
                Collections.singletonList(new ImageFontText("开始时间: 2020-10-20", timeConfig)));

        ImageFont endTimeFont = new ImageFont(1100, 1215, 355,
                Collections.singletonList(new ImageFontText("结束时间: 2021-10-20", timeConfig)));

        canvas.drawFont(beginTimeFont);
        canvas.drawFont(endTimeFont);
    }

    private static void drawBatchCode(int times, String batchCode) throws IOException {

        int timeSize = 24 * times;

        ImageDrawConfig timeConfig = ImageDrawConfig.builder()
                .font(new Font(font, Font.PLAIN, timeSize))
                .fontColor(new Color(0xD90622))
                .build();

        ImageFont imageFont = new ImageFont(1090, 2130, 0,
                Collections.singletonList(new ImageFontText(batchCode, timeConfig)));

        canvas.drawFont(imageFont, false);
    }

    private static File getFile(String fileName) {

        // 若文件不存在，则 clear 一下，再重新编译
        URL resource = ImageCanvasTest.class.getResource(fileName);
        return new File(resource.getFile());
    }

    public static void main(String[] args) throws IOException {

        int times = 3;

        long time1 = System.currentTimeMillis();

        drawName(times, "拌面5元券测试");
        long time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);

        drawDesc(times, 20, 10);
        time1 = System.currentTimeMillis();
        System.out.println(time2 - time1);

        drawTime(times);
        time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);

        drawBatchCode(times, "2020");
        time1 = System.currentTimeMillis();
        System.out.println(time2 - time1);

        BufferedImage image = canvas.getImage();

        File imageFile = getFile("image.png");

        canvas.drawImage(imageFile, image.getWidth() / 2, 1480, (int) (738 * 1.2), (int) (303 * 1.2));
        canvas.drawImage(imageFile, 520, 2070, 360, 360);

        time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);

        ImageIO.write(image, "png", new File("D:/xiaohe66/test_bg.png"));
    }
}