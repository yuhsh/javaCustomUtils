package org.custom.utils;

import java.io.File;
import java.io.IOException;

public class CreateFileUtil {
    
    /**
     * 创建文件
     * @param destFileName 目标文件
     * @return
     */
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        // 判断文件是否存在
        if (file.exists()) {
            return false;
        }
        // 目标文件不能为文件夹
        if (destFileName.endsWith(File.separator)) {
            return false;
        }
        // 判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            // 如果目标文件所在目录不存在，则创建父目录
            if (!file.getParentFile().mkdirs()) {
                return false;
            }
        }
        // 创建文件
        try {
            if (file.createNewFile()) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 创建目录
     * @param destDirName
     * @return
     */
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        // 目标目录存在性检查
        if (dir.exists()) {
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        // 创建目录
        if (dir.mkdirs()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 创建临时文件
     * @param prefix
     * @param suffix
     * @param dirName
     * @return
     */
    public static String createTempFile(String prefix, String suffix, String dirName) {
        File tempFile = null;
        if (dirName == null) {
            try {
                // 在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                // 返回临时文件的路径
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File dir = new File(dirName);
            // 如果临时文件所在目录不存在，进行创建
            if (!dir.exists()) {
                if (!createDir(dirName)) {
                    return null;
                }
            }
            try {
                // 在指定目录下创建临时文件
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        return null;
    }

}
