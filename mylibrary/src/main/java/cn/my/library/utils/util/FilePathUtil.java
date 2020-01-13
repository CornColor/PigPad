package cn.my.library.utils.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * created by dan
 */
public class FilePathUtil {
    /**
     * 获取存储路径
     * @param context
     * @param dir
     * @return
     */
    public static String getFilePath(Context context, String dir){
        String directoryPath = "";
        //判断sd卡是否可用
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            directoryPath = context.getExternalFilesDir(dir).getAbsolutePath();
        }else {
            directoryPath = context.getFilesDir()+ File.separator+dir;
        }
        return directoryPath;
    }
}
