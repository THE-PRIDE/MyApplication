package com.meng.myapplication.MyFile;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by LMY on 18/7/5.
 *
 *
 * 外部存储（external storage）
   Environment.getExternalStorageDirectory()   SD根补录/sdcard/(6.0后需要用户授权)
   context.getExternalFileDir(dir)	     路径：sdcard/Android/data/<package name>/files/..
   context.getExternalCacheDir      路径：sdcard/Android/data/<package name>/cach/..

   内部存储（internal storage）
   context.getFilesDir()	路径: data/data<package name>/files/..
   context.getCacheDir()	路径：data/data<package name>/cach/..
 */

public class MyFileUtils {

    /**
     *
     */
    public static String getFilePath(Context context, String dir) {
        String directoryPath="";
        //判断SD卡是否可用
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ) {
            directoryPath =context.getExternalFilesDir(dir).getAbsolutePath() ;
            // directoryPath =context.getExternalCacheDir().getAbsolutePath() ;
        }else{
        //没内存卡就存机身内存
            directoryPath=context.getFilesDir()+ File.separator+dir;
            // directoryPath=context.getCacheDir()+File.separator+dir;
        }
        File file = new File(directoryPath);
        if(!file.exists()){//判断文件目录是否存在
            file.mkdirs();
        }
        Log.i("MY","filePath====>"+directoryPath);
        return directoryPath;
    }
}
