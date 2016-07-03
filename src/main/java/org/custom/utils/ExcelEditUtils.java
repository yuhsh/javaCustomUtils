/**
 * Excel插入印章工具类
 */
package org.custom.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import jxl.write.WritableImage;
import jxl.write.WritableSheet;

public class ExcelEditUtils {

  /**
   * 在指定excel的sheet指定位置插入印章图片
   * @param userName 印章人员
   * @param endTime  印章日期
   * @param sealSheet 插入印章sheet
   * @param cellRow  插入印章指定行
   * @param cellCol  插入印章指定列
   * @throws IOException
   */
  public static void addSealToExcel(
    String userName, String endTime,
    WritableSheet sealSheet, double cellRow, double cellCol) throws IOException {
    // 开始位置
    double picBeginCol = cellCol - 1;
    double picBeginRow = cellRow - 1;
    // 图片时间的高度，宽度
    double picCellWidth = 0.0;
    double picCellHight = 0.0;
    // 读入图片
    CreateSealUtils seal = new CreateSealUtils();
    BufferedImage picImage = seal.export2BufferedImage(userName, endTime);
    
    // 取得图片的像素高度，宽度
    int picWidth = picImage.getWidth();
    int picHeight = picImage.getHeight();
    // 计算图片时间的宽度
    int picWidth_t = picWidth * 32;
    for (int x = 0; x < 1234; x++) {
      int bc = (int)Math.floor(picBeginCol + x);
      // 得到单元格的宽度
      int v = sealSheet.getColumnView(bc).getSize();
      double offset0_t = 0.0;
      if (0 == x) {
        offset0_t = (picBeginCol - bc) * v;
      }
      if (0.0 + offset0_t + picWidth_t > v) {
        // 剩余宽度超过一个单元格的宽度
        double ratio_t = 1.0;
        if (0 == x) {
          ratio_t = (0.0 + v - offset0_t) / v;
        }
        picCellWidth += ratio_t;
        picWidth_t -= (int)(0.0 + v - offset0_t);
      } else {
        // 剩余宽度不足一个单元格的宽度
        double ratio_r = 0.0;
        if (v != 0) {
          ratio_r = (0.0 + picWidth_t) / v;
          picCellWidth += ratio_r;
          break;
        }
      }
    }
    // 计算图片的实际高度
    int picHeight_t = picHeight * 15;
    for (int x = 0; x < 1234; x++) {
      int bc = (int)Math.floor(picBeginRow + x);
      // 得到单元格的高度
      int v = sealSheet.getRowView(bc).getSize();
      double offset0_r = 0.0;
      if (0 == x) {
        offset0_r = (picBeginRow - bc) * v;
      }
      if (0.0 + offset0_r + picHeight_t > v) {
        // 剩余高度超过一个单元格的高度
        double ratio_q = 1.0;
        if (0 == x) {
          ratio_q = (0.0 + v + offset0_r) / v;
        }
        picCellHight += ratio_q;
        picHeight_t -= (int)(0.0 + v - offset0_r);
      } else {
        // 剩余高度不足一个单元格的高度
        double ratio_m = 0.0;
        if (v != 0) {
          ratio_m = (0.0 + picHeight_t) / v;
        }
        picCellHight += ratio_m;
        break;
      }
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(picImage, CreateSealUtils.FILETYPE, baos);

    WritableImage image = new WritableImage(picBeginCol,picBeginRow,picCellWidth,picCellHight,baos.toByteArray());//列，行，跨越行，跨越列
    sealSheet.addImage(image);
  }
}
