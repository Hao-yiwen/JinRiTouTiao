package io.github.haoyiwen.jinritoutiao.utils;

import android.os.Environment;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;

public class FileUtils {

    public static final String ROOT_DIR = "Android/data/"
            + UIUtils.getPackageName();
    public static final String DOWNLOAD_DIR = "download";
    public static final String CACHE_DIR = "cache";
    public static final String ICON_DIR = "icon";

    public static final String APP_STORAGE_ROOT = "JINRITouTiao";


    public static String getImageFileExt(String filePath) {
        HashMap<String, String> mFileTypes = new HashMap<>();
        mFileTypes.put("FFD8FF", ".jpg");
        mFileTypes.put("89504E47", ".png");
        mFileTypes.put("474946", ".gif");
        mFileTypes.put("49492A00", ".tif");
        mFileTypes.put("424D", ".bmp");

        String value = mFileTypes.get(getFileHeader(filePath));
        String ext = TextUtils.isEmpty(value) ? ".jpg" : value;
        Logger.i("file ext:" + ext);
        return ext;
    }

    private static String getFileHeader(String filePath) {
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[3];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return value;
    }

    public static String getAppExternalStoragePath() {
        // 获取应用的外部存储路径
        File file = UIUtils.getContext().getExternalFilesDir(null);
        if (file != null) {
            return file.getAbsolutePath() + File.separator + APP_STORAGE_ROOT + File.separator;
        } else {
            return null;
        }
    }

    // 示例 SD 卡是否可用方法
    public static boolean isSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getCachePath() {
        File f = UIUtils.getContext().getCacheDir();
        if (null == f) {
            return null;
        } else {
            return f.getAbsolutePath() + "/";
        }
    }

    public static boolean createDirs(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return true;
    }

    public static String getDir(String name) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            sb.append(getAppExternalStoragePath());
        } else {
            sb.append(getCachePath());
        }
        // 确保 name 变量前后没有 File.separator
        if (!name.startsWith(File.separator)) {
            sb.append(File.separator);
        }
        sb.append(name);
        if (!name.endsWith(File.separator)) {
            sb.append(File.separator);
        }
        String path = sb.toString();
        if (createDirs(path)) {
            return path;
        } else {
            return null;
        }
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        String header = builder.toString();
        Logger.i("file header:" + header);
        return header;
    }

    public static void copy(File file, File destFile) throws Exception {
        if (!file.exists()) {
            throw new FileNotFoundException("Source file does not exist");
        }

        try (FileInputStream inputStream = new FileInputStream(file);
             FileOutputStream outputStream = new FileOutputStream(destFile);
             FileChannel inChannel = inputStream.getChannel();
             FileChannel outChannel = outputStream.getChannel()) {
            inChannel.transferTo(0L, inChannel.size(), outChannel);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(e.getMessage());
            throw e; // 重新抛出异常，以便调用者知道发生了什么问题
        }
    }
}
