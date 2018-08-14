package com.meng.myapplication.glidetest;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.meng.myapplication.R;

/**
 * Created by LMY on 18/8/2.
 *
 */

public class GlideImageTestActivity extends AppCompatActivity{

    private Button mBtnGlideTest,BtnGlideTest2;
    private ImageView mImgGlideView;

    private String mGlideURL;

    private PopupWindow popupWindow = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_glide_view);
        initView();
        initData();
        initListener();

    }

    private void initView(){
        mBtnGlideTest = findViewById(R.id.BtnGlideTest);
        BtnGlideTest2 = findViewById(R.id.BtnGlideTest2);
        mImgGlideView = findViewById(R.id.ImgGlideTest);
    }
    private void initData(){

        mGlideURL = "";
    }
    private void initListener(){
        mBtnGlideTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                show(view);//Popwindow
                Intent intent = new Intent(GlideImageTestActivity.this,ImageViewActivity.class);
                String url = "http://28.5.2.72:8080/NMBFOServer/201.png";
                intent.putExtra("URL",url);
                startActivity(intent);
            }
        });
        BtnGlideTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://28.5.2.72:8080/NMBFOServer/20181.png";
                Intent intent = new Intent(GlideImageTestActivity.this,ImageViewActivity.class);
                intent.putExtra("URL",url);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TGA","resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TGA","pause");
    }


    private void initPopupwindow(){

        View view  = LayoutInflater.from(GlideImageTestActivity.this).inflate(R.layout.view_popupwindow_layout,null);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        popupWindow = new PopupWindow(view ,WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
        popupWindow.setAnimationStyle(R.style.popwindow_anim_style);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);

    }


    private void showPopupWindow(){
        if (popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
            popupWindow = null;
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.alpha = 1.0f;
            getWindow().setAttributes(params);
        } else {
            initPopupwindow();
        }
    }

    private void show(View parent){
        showPopupWindow();
        popupWindow.showAtLocation(parent, Gravity.CENTER,0,0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showPopupWindow();
    }
}
