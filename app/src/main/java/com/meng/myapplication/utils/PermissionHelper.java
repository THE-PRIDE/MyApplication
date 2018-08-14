package com.meng.myapplication.utils;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Build;
import android.support.v4.app.Fragment;

import java.util.List;

import static com.meng.myapplication.utils.PermissionUtils.getDeniedPermissions;

/**
 * Created by LMY on 18/5/7.
 *
 * 反射+注解 实现运行时权限请求
 *
 * 不用反射注解了，改用监听
 * 链式调用  高B格
 *
 */

public class PermissionHelper {

    private Object mObject;
    private int mRequestCode;
    private String [] mRequestPermission;
    private final static String TAG = "permissionFragment";

    private PermissionHelper(Object object){
        this.mObject = object;
    }

    //链式方法
    public static PermissionHelper with(Activity activity){
        return new PermissionHelper(activity);
    }

    public static PermissionHelper with(Fragment fragment){
        return new PermissionHelper(fragment);
    }

    public PermissionHelper requestCode(int requestCode){
        this.mRequestCode = requestCode;
        return this;
    }

    public PermissionHelper requestPermission(String... permissions){
        this.mRequestPermission = permissions;
        return this;
    }

    private PermissionListener permissionListener;

    public PermissionHelper setOnResultListener(PermissionListener permissionListener){
        this.permissionListener = permissionListener;
        return this;
    }

    private PermissionFragment permissionFragment;

    /**
     * 发送申请权限请求
     */
    public void requestForFragment(){
        permissionFragment = getRxPermissionsFragment((Activity) mObject);
        permissionFragment.setPermissionListener(permissionListener);
        permissionFragment.requestPermission(mRequestPermission,mRequestCode);
    }

    private PermissionFragment getRxPermissionsFragment(Activity activity) {
        PermissionFragment permissionFragment = findRxPermissionsFragment(activity);
        boolean isNewInstance = permissionFragment == null;
        if (isNewInstance) {
            permissionFragment = new PermissionFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(permissionFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return permissionFragment;
    }

    private PermissionFragment findRxPermissionsFragment(Activity activity) {
        return (PermissionFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }






//    public void request(){
//
//        // TODO 参数做下非空处理
//
//        if (Build.VERSION.SDK_INT < 23){
//            //如果系统为6.0以下，直接调用执行成功方法
//            PermissionUtils.executeSucceedMethod(mObject,mRequestCode);
//        }else {
//            //6.0以上，先判断权限是否已经获取到
//            List<String> deniedPermissions = getDeniedPermissions(mObject,mRequestPermission);
//            if (deniedPermissions.size() == 0){
//                PermissionUtils.executeSucceedMethod(mObject,mRequestCode);
//            } else if(deniedPermissions.size() == 1){
//                PermissionUtils.requestPermission((Activity) mObject,mRequestPermission[0],mRequestCode);
//            } else {
//                PermissionUtils.requestPermissions((Activity) mObject,mRequestPermission,mRequestCode);
//            }
//        }
//    }



}
