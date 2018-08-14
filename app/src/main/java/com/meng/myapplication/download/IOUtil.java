package com.meng.myapplication.download;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by LMY on 18/6/14.
 *
 */

public class IOUtil {

    public static void closeAll(Closeable... closeables){
        if (closeables == null){
            return;
        }
        for (Closeable closeable : closeables){
            if (closeable != null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void showToast(Context context){
        Toast.makeText(context,"asdf",Toast.LENGTH_SHORT).show();
    }


    public static void readFile(){
        String pathString = Environment.getExternalStorageDirectory().getAbsolutePath();
        try {
            pathString = URLDecoder.decode(pathString,"UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        File file = new File(pathString,"test.txt");
        InputStream is = null;

        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer("");
        String b = "";

        try {
            while ((b = br.readLine()) != null){
                sb.append(b).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String bb = sb.toString();
        Log.e("MY",bb);
    }
}
