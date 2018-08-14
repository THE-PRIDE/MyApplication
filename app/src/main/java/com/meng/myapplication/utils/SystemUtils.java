package com.meng.myapplication.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.Locale;

/**
 * Created by LMY on 18/5/7.
 *
 */

public class SystemUtils {

    /**
     * 获取当前系统语言
     * @return
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前手机系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     *
     */
    @SuppressLint("NewApi")
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }else{
                if (Build.VERSION.SDK_INT >= 23){
                    return tm.getImei();
                }else{
                    return tm.getDeviceId();
                }
            }
        }
        return null;
    }

    /**
     * 像素转换成DIP（Device Independent Pixels 设备独立像素）
     * @param context 上下文
     * @param pixels  像素
     * @return  dp值
     */
    public static float pxToDp(Context context,int pixels){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        //第二种方法，可能部分设备取值会有问题
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = windowManager.getDefaultDisplay();
//        Point point = new Point();
//        DisplayMetrics metrics1 = new DisplayMetrics();
//        display.getSize(point);
//        display.getMetrics(metrics1);

        return pixels/(metrics.density/DisplayMetrics.DENSITY_DEFAULT);
    }
}
