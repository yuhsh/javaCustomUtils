/**
 * 文件操作工具类
 */
package org.custom.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
    private static final String FOLDER_SEPARATOR = "/";
    private static final char EXTENSION_SEPARATOR = '.';

    /**
     * 复制文件或文件夹
     * @param inputFile 源文件
     * @param outputFile 目标文件
     * @param isOverWrite 是否覆盖（只针对文件）
     * @throws IOException
     */
    public static void copy(File inputFile, File outputFile, boolean isOverWrite)
            throws IOException {
        if (!inputFile.exists()) {
            throw new RuntimeException(inputFile.getPath() + "源目录不存在!");
        }
        copyPri(inputFile, outputFile, isOverWrite);
    }

    /**
     * 递归copy文件或文件夹
     * @param inputFile 源文件
     * @param outputFile 目标文件
     * @param isOverWrite 是否覆盖（只针对文件）
     * @throws IOException
     */
    private static void copyPri(File inputFile, File outputFile,
            boolean isOverWrite) throws IOException {
        // 是个文件。
        if (inputFile.isFile()) {
            copySimpleFile(inputFile, outputFile, isOverWrite);
        } else {
            // 文件夹
            if (!outputFile.exists()) {
                outputFile.mkdir();
            }
            // 循环子文件夹
            for (File child : inputFile.listFiles()) {
                copy(child,
                        new File(outputFile.getPath() + "/" + child.getName()),
                        isOverWrite);
            }
        }
    }

    /**
     * copy单个文件
     * @param inputFile 源文件
     * @param outputFile 目标文件
     * @param isOverWrite 是否覆盖（只针对文件）
     * @throws IOException
     */
    private static void copySimpleFile(File inputFile, File outputFile,
            boolean isOverWrite) throws IOException {
        // 目标文件已经存在
        if (outputFile.exists()) {
            if (isOverWrite) {
                if (!outputFile.delete()) {
                    throw new RuntimeException(outputFile.getPath() + "无法覆盖！");
                }
            } else {
                // 不允许覆盖
                return;
            }
        }
        InputStream in = new FileInputStream(inputFile);
        OutputStream out = new FileOutputStream(outputFile);
        byte[] buffer = new byte[1024];
        int read = 0;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        in.close();
        out.close();
    }

    /**
     * 删除文件
     * @param file
     */
    public static void delete(File file) {
        deleteFile(file);
    }

    /**
     * 递归删除文件
     * @param file 文件
     * @return boolean true 删除成功，false 删除失败。
     */
    private static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        // 单文件
        if (!file.isDirectory()) {
            boolean delFlag = file.delete();
            if (!delFlag) {
                throw new RuntimeException(file.getPath() + "删除失败！");
            } else {
                return;
            }
        }
        // 删除子目录
        for (File child : file.listFiles()) {
            deleteFile(child);
        }
        // 删除自己
        file.delete();
    }

    /**
     * 从文件路径中抽取文件扩展名
     * @param path 文件路径
     * @return 如果path为null，直接返回null。
     */
    public static String getFilenameExtension(String path) {
        if (CheckUtils.isNullOrEmpty(path)) {
            return null;
        }
        int extIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
        if (extIndex == -1) {
            return null;
        }
        int folderIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        if (folderIndex > extIndex) {
            return null;
        }
        return path.substring(extIndex + 1);
    }

    /**
     * 从文件路径中抽取文件名
     * @param path 文件路径
     * @return 抽取出来的文件名, 如果path为null，直接返回null。
     */
    public static String getFilename(String path) {
        if (CheckUtils.isNullOrEmpty(path)) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1)
                : path);
    }

    /**
     * 保存文件
     * @param content 字节
     * @param file 保存到文件
     * @throws IOException
     */
    public static void save(byte[] content, File file) throws IOException {
        if (CheckUtils.isNull(file)) {
            throw new RuntimeException("保存文件不能为空");
        }
        if (CheckUtils.isNull(content)) {
            throw new RuntimeException("文件流不能为空");
        }
        InputStream is = new ByteArrayInputStream(content);
        save(is, file);
    }

    /**
     * 保存文件
     * @param inputStream 文件流
     * @param file 保存到文件
     * @throws IOException
     */
    public static void save(InputStream inputStream, File file) throws IOException {
        if (CheckUtils.isNull(file)) {
            throw new RuntimeException("保存文件不能为空");
        }
        if (CheckUtils.isNull(inputStream)) {
            throw new RuntimeException("文件流不能为空");
        }
        // 输出流
        OutputStream streamOut = null;
        // 文件夹不存在就创建。
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        streamOut = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            streamOut.write(buffer, 0, bytesRead);
        }
        streamOut.close();
        inputStream.close();
    }
}
