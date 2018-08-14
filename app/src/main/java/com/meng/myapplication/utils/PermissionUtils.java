package com.meng.myapplication.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale;


/**
 * Created by LMY on 18/5/4.
 * 权限工具类
 */

public class PermissionUtils {

    /**
     * CALENDAR
     * READ_CALENDAR
     * WRITE_CALENDAR
     * CAMERA
     * CAMERA
     * CONTACTS
     * READ_CONTACTS
     * WRITE_CONTACTS
     * GET_ACCOUNTS
     * LOCATION
     * ACCESS_FINE_LOCATION
     * ACCESS_COARSE_LOCATION
     * MICROPHONE
     * RECORD_AUDIO
     * PHONE
     * READ_PHONE_STATE
     * CALL_PHONE
     * READ_CALL_LOG
     * WRITE_CALL_LOG
     * ADD_VOICEMALL
     * USE_SIP
     * PROCESS_OUTGOING_CALLS
     * SENSORS
     * BODY_SENSORS
     * SMS
     * SEND_SMS
     * RECEIVE_SMS
     * READ_SMS
     * RECEIVE_WAP_PUSH
     * RECEIVE_MMS
     * <p>
     * STORAGE
     * READ_EXTERNAL_STORAGE
     * WRITE_EXTERNAL_STORAGE
     * <p>
     * 九类危险权限在android6.0之后，需动态申请。
     * 同一组内任何一个权限被授权了，其他权限也自动被授权
     * <p>
     * <p>
     * 可以同时申请多个权限，但不建议
     * <p>
     * <p>
     * shouldShowRequestPermissionRationale--此方法在APP第一次需要获取权限时，返回false，
     * 当用户拒绝授予权限后，再次请求权限时，返回true，告知用户为何需要权限
     * <p>
     * <p>
     * 魅族PRO 6S会直接返回true,需单独处理
     */

    public final static int CAMERA_REQUEST_CODE = 1;

    /**
     * 申请一个权限，建议使用
     * @param activity      上下文
     * @param PermissionName       权限名称
     * @param requestCode       请求码
     */
    static void requestPermission(Activity activity, String PermissionName, int requestCode) {

        final Activity context;
        context = activity;
        final String name;
        name = PermissionName;
        final int code;
        code = requestCode;
//        if (ContextCompat.checkSelfPermission(activity, PermissionName)
//                != PackageManager.PERMISSION_GRANTED) {
            //第一次获取权限时，返回false，之后返回true
            if (shouldShowRequestPermissionRationale(activity, PermissionName)) {
                new AlertDialog.Builder(activity)
                        .setMessage("不给权限，不能干活")
                        .setNegativeButton("不给", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executeFailedMethod(context,code);
                            }
                        })
                        .setPositiveButton("给", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(context, new String[]{name}, code);
                            }
                        }).show();
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{PermissionName}, requestCode);
            }
//        } else {
//            executeSucceedMethod(activity,code);
//            Toast.makeText(activity, "权限已申请", Toast.LENGTH_SHORT).show();
//        }
    }

    /**
     * 一次申请多个权限，不建议使用-------有问题
     * @param activity      上下文
     * @param permissionName       需要申请的所有权限
     * @param requestCode      请求码
     */
    static void requestPermissions(Activity activity,String[] permissionName,int requestCode){

        // TODO 同时申请多个权限时，只能通过一个，测试机是三星，其他手机没测
        List<String> permissions = getDeniedPermissions(activity,permissionName);
        if (permissions.size() <= 0){
            executeSucceedMethod(activity,requestCode);
        }else{
            //没有授权的权限
            String[] permissionArray = new String[permissions.size()];
            permissionArray = permissions.toArray(permissionArray);
//            String[] PermissionString = (String[]) Array.newInstance(String.class,permissions.size());
            ActivityCompat.requestPermissions(activity, permissionArray, requestCode);
        }
    }



    public static void executeSucceedMethod(Object reflectObject, int requestCode) {
        //获取class中所有的方法
        Method[] methods = reflectObject.getClass().getDeclaredMethods();

        for (Method method : methods) {
            PermissionSuccessed successed = method.getAnnotation(PermissionSuccessed.class);
            if (successed != null) {
                int methodCode = successed.requestCode();
                if (methodCode == requestCode) {
                    executeMethod(reflectObject, method);
                }
            }
        }
    }

    private static void executeFailedMethod(Object reflectObject,int requestCode){

        Method[] methods = reflectObject.getClass().getDeclaredMethods();
        for (Method method : methods){
            PermissionFailed failed = method.getAnnotation(PermissionFailed.class);
            if (failed != null){
                int methodCode = failed.requestCode();
                if (methodCode == requestCode){
                    executeMethod2(reflectObject,method);
                }
            }
        }
    }

    /**
     * @param reflectObject 反射对象
     * @param method        授权成功调用的方法
     */
    private static void executeMethod(Object reflectObject, Method method) {
        try {
            method.setAccessible(true);//允许执行私有方法
            method.invoke(reflectObject, new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void executeMethod2(Object reflectObject, Method method){

        try {
            method.setAccessible(true);
            method.invoke(reflectObject, new Object[]{});
        } catch (IllegalAccessException e){
            e.printStackTrace();
        } catch (InvocationTargetException e){
            e.printStackTrace();
        }
    }


    /**
     * 判断申请的权限列表中，有哪些是没有授权的
     *
     * @param mObject            上下文
     * @param mRequestPermission 所需要申请的权限
     * @return 未授权的的权限
     */
    static List<String> getDeniedPermissions(Object mObject, String[] mRequestPermission) {
        List<String> stringList = new ArrayList<>();
        for (String string : mRequestPermission) {
            if (ActivityCompat.checkSelfPermission((Context) mObject, string)
                    != PackageManager.PERMISSION_GRANTED){
                stringList.add(string);
            }
        }
        return stringList;
    }


}
