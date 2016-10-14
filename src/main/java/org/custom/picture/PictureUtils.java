package org.custom.picture;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PictureUtils {

    // Image,ImageIO,BufferedImage,Icon,ImageIcon
    public static void  createBufferImage() {
        // 图片缓冲区
        BufferedImage bufferedImage = new BufferedImage(70, 35, BufferedImage.TYPE_INT_RGB);
        // 绘制环境
        Graphics2D graphics2d = (Graphics2D) bufferedImage.getGraphics();
        graphics2d.setColor(Color.WHITE);// 设置颜色
        graphics2d.fillRect(0, 0, 70, 35);//设置背景
        graphics2d.setColor(Color.YELLOW);// 设置颜色
        graphics2d.drawRect(0, 0, 70-1, 35-1);//边框
        graphics2d.setFont(new Font("宋体",Font.PLAIN, 12));// 字体样式字号
        graphics2d.setColor(Color.BLACK); // 字体颜色
        graphics2d.drawString("HelloWorld", 3, 20); // 文本内容
        try {
            ImageIO.write(bufferedImage, "JPEG", new FileOutputStream("../a.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
