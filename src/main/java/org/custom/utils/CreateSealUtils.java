/**
 * 印章生成工具类
 * @author yuhaisheng
 */
package org.custom.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.custom.constant.UtilConstants;

public class CreateSealUtils {

  /** 印章宽度 */
  private int width = 96;
  /** 印章高度 */
  private int height = 96;
  /** 输入文字字体设置 */
  private Font dateFont = new Font("宋体", Font.PLAIN, 18);
  /**  */
  private String lettering = "印";
  /** 文件后缀 */
  private String fileSuffix = ".png";
  /** 文件类型 */
  public static final  String FILETYPE = "png";

  /**
   * 绘制印章
   * @param g2d
   * @param userName 印章用户名
   * @param endTime  印章时间
   */
  public void draw(Graphics2D g2d, String userName, String date) {
    // 把绘制起点挪到圆中心点
    g2d.translate(width/2, height/2);
    // 绘制印章边框
    g2d.setColor(Color.RED);             // 指定边框颜色
    g2d.setStroke(new BasicStroke(1.5F));// 指定边框宽度
    g2d.setRenderingHint(                // 去除锯齿
      RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.drawOval(-width/2, -height/2, width, height);  // 描画

    // 绘制发起人
    g2d.setFont(new Font("宋体", Font.PLAIN, 20));
    g2d.setColor(Color.RED);
    FontMetrics fm = g2d.getFontMetrics();
    int w = fm.stringWidth(userName);
    int h = fm.getHeight();
    int y = fm.getAscent() -h/2;
    g2d.drawString(userName, -w/2, y-20);
    Line2D lin = new Line2D.Float(-92/2, y-15, 92/2, y-15);
    g2d.draw(lin);

    // 绘制发起日期
    g2d.setFont(dateFont);
    g2d.setColor(Color.RED);
    fm = g2d.getFontMetrics();
    w = fm.stringWidth(date);
    h = fm.getHeight();
    y = fm.getAscent() -h/2;
    g2d.drawString(date, -w/2, y+2);
    lin = new Line2D.Float(-92/2, y+5, 92/2, y+5);
    g2d.draw(lin);

    // 绘制印字
    g2d.setFont(new Font("宋体", Font.PLAIN, 20));
    g2d.setColor(Color.RED);
    fm = g2d.getFontMetrics();
    w = fm.stringWidth(lettering);
    h = fm.getHeight();
    y = fm.getAscent() -h/2;
    g2d.drawString(lettering, -w/2, y+28);
  }

  /**
   * 导出此印章为透明背景的图片流.
   * @param userName 印章用户名
   * @param endTime  印章时间
   * @return 图片流
   * @throws IOException 写出图像数据出现问题
   */
  public BufferedImage export2BufferedImage(String userName, String endTime) throws IOException {
    int fix = 2;// 宽高修正,如果宽高就为图片宽高,可能边框线被切割
    BufferedImage bi = new BufferedImage(getWidth() + fix * 2, getHeight()
        + fix * 2, BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g2d = bi.createGraphics();
    g2d.translate(fix, fix);
    this.draw(g2d, userName, endTime);
    g2d.dispose();
    return bi;
  }

  /**
   * 导出此印章为透明背景的图片字节数组.
   * @param format 图片类型,如果为null,则默认为png
   * @param userName 印章用户名
   * @param endTime  印章时间
   * @return 数组
   * @throws IOException 写出图像数据出现问题
   */
  public byte[] export2ImageByte(String format, String userName, String endTime) throws IOException {
    int fix = 2;// 宽高修正,如果宽高就为图片宽高,可能边框线被切割
    BufferedImage bi = new BufferedImage(getWidth() + fix * 2, getHeight()
        + fix * 2, BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g2d = bi.createGraphics();
    g2d.translate(fix, fix);
    this.draw(g2d, userName, endTime);
    g2d.dispose();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bi, format == null ? FILETYPE : format, baos);
    return baos.toByteArray();
  }

  
  /**
   * 生成印章图片，返回印章图片路径
   * @param pathString 图片生成路径
   * @param userName 印章用户名
   * @param endTime  印章时间
   * @return 数组
   * @throws IOException 写出图像数据出现问题
   */
  public String export2Image(String pathString, String userName, String endTime) throws IOException {
    int fix = 2;// 宽高修正,如果宽高就为图片宽高,可能边框线被切割
    BufferedImage bi = new BufferedImage(getWidth() + fix * 2, getHeight()
        + fix * 2, BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g2d = bi.createGraphics();
    g2d.translate(fix, fix);
    this.draw(g2d, userName, endTime);

    // 生成文件名
    String date = DateUtils.format(DateUtils.getNow(), UtilConstants.DATE_FORMAT_YYYYMMDDHHMMSS);
    String fileName = date  + fileSuffix;
    String filePath = pathString + File.separator + fileName;

    ImageIO.write(bi, FILETYPE, new File(filePath));
    g2d.dispose();
    return filePath;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
