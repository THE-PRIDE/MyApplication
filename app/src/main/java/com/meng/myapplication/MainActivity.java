package com.meng.myapplication;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.meng.myapplication.download.DownloadInfo;
import com.meng.myapplication.download.DownloadManager;
import com.meng.myapplication.download.DownloadObserver;
import com.meng.myapplication.utils.PermissionHelper;
import com.meng.myapplication.utils.PermissionListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView mTvCamera;
    private TextView mTvPermissinoJar;
    private TextView mTvNotify;
    private TextView mTvPhoneInfo;

    private ImageView mImgDownload;

//    private String url1 = "http://22.5.238.10/NMBFOServer/WebMB/3DResource/android/licai.png";
    private String url = "http://22.5.238.10/NMBFOServer/WebMB/3DResource/andriod/licai.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("MY","onCreate");

        setContentView(R.layout.activity_main);
        initView();
//        initData();
        initListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("MY","onRestart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MY","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MY","onDestroy");

    }

    private void initView() {
        mTvCamera = findViewById(R.id.tv_camera);
        mTvPermissinoJar = findViewById(R.id.tv_permission_jar);
        mTvNotify = findViewById(R.id.tv_notify);
        mTvPhoneInfo = findViewById(R.id.tv_phone_info);
        mImgDownload = findViewById(R.id.ImgDownload);

        JSONObject jsonObject = new JSONObject();
        try {
            Object a = jsonObject.get("a");
            Log.e("MY",a+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initData() {


//        VectorDrawable vectorDrawable = VectorDrawable.

        String channelID = "The First";
        if (Build.VERSION.SDK_INT >= 26){
            NotificationChannel channel = new NotificationChannel(channelID,"a", NotificationManager.IMPORTANCE_HIGH);
            channel.setLightColor(Color.BLUE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(this)
                    .setContentTitle("")
                    .setContentText("")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setChannelId(channelID)
                    .build();
            manager.notify(1,notification);
            //通知标志  通知圆点   长按图标查看   O版本原生提供此功能
        }





//        View view = new View(this);
//        view.getTop();
//        view.getX();
//        view.getTranslationX();

//        MotionEvent event = MotionEvent.obtain();
//                event.getX();//view的坐标
//                event.getRawX();//相对于屏幕的坐标
//
//        VelocityTracker velocityTracker = VelocityTracker.obtain();
//        velocityTracker.addMovement(event);
//
//        velocityTracker.computeCurrentVelocity(1000);
//        int xVelocity = (int) velocityTracker.getXVelocity();
//        int yVelocity = (int) velocityTracker.getYVelocity();
//
//        //不需要的时候，调用clear方法，重置并回收内存
//        velocityTracker.clear();
//        velocityTracker.recycle();

//        RxPermissions rxPermissions = new RxPermissions(this);
//        rxPermissions.request(Manifest.permission.CALL_PHONE,Manifest.permission.CAMERA).subscribe(
//                new Observer<Boolean>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Boolean aBoolean) {
//                        if (aBoolean){
//                            hasGet();
//                        } else {
//                            refuse();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                }
//        );
    }

    private void initListener() {
        mTvCamera.setOnClickListener(this);
        mTvPermissinoJar.setOnClickListener(this);
        mTvNotify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_camera:
//                PermissionUtils.requestPermissionCamera(this,
//                        Manifest.permission.READ_CALENDAR,
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.READ_CONTACTS,
//                        Manifest.permission.READ_CONTACTS,
//                        Manifest.permission.READ_CONTACTS,
//                        Manifest.permission.READ_CONTACTS,
//                        Manifest.permission.READ_CONTACTS,
//                        Manifest.permission.READ_CONTACTS,
//                        1);

                PermissionHelper.with(this).requestPermission(Manifest.permission.READ_PHONE_STATE)
                        .requestCode(1).setOnResultListener(new PermissionListener() {
                    @Override
                    public void success() {
                        Toast.makeText(MainActivity.this, "fragment---accept", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failed() {
                        //申请失败，是否提示用户打开权限设置界面，进行手动设置
                        openPermissionSetting();
//                      Toast.makeText(MainActivity.this, "fragment---refuse", Toast.LENGTH_SHORT).show();
                    }
                }).requestForFragment();

            break;
            case R.id.tv_permission_jar:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.requestEach(Manifest.permission.READ_CONTACTS)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                if (permission.granted){
                                    Toast.makeText(MainActivity.this, "rxPermission---accept", Toast.LENGTH_SHORT).show();

                                } else if(permission.shouldShowRequestPermissionRationale){
                                    Toast.makeText(MainActivity.this, "rxPermission---refuse", Toast.LENGTH_SHORT).show();
                                    //拒绝权限，但没选中【不再询问】
                                    //部分手机，默认不再询问

                                } else {
                                    //拒绝权限，选中【不再询问】
                                    Toast.makeText(MainActivity.this, "rxPermission---noAsk", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                break;

            case R.id.tv_notify:

                DownloadManager.getInstance().download(url, new DownloadObserver() {

                    @Override
                    public void onNext(DownloadInfo downloadInfo) {
                        super.onNext(downloadInfo);
                        downloadInfo.getProgress();

                    }

                    @Override
                    public void onComplete() {
                        if (downloadInfo != null){
                            File file = downloadInfo.getDownFile();
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                            mImgDownload.setImageBitmap(bitmap);
                            Toast.makeText(MainActivity.this, file.getPath(),Toast.LENGTH_SHORT).show();
//                            Toast.makeText(MainActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }


    // TODO 打开权限设置界面 部分手机只能查看权限不能设置，比如vivo OPPO，需验证
    private void openPermissionSetting(){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9){
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package",getPackageName(),null));
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName",getPackageName());
        }
        startActivity(intent);
    }

}
