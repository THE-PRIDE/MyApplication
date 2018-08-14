package com.meng.myapplication.UI;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.meng.myapplication.R;

/**
 * Created by LMY
 *
 * 控制Item排列方式 ，LayoutManager
 * 适配器  RecyclerView.Adapter
 * 控制Item间隔 RecyclerView.ItemDecoration
 * 控制Item增删动画，RecyclerView.ItemAnimator
 *
 *
 */

public class ListActivity extends AppCompatActivity {

    private RecyclerView mRycList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
        initData();
        initListener();
    }

    private void initView(){
        mRycList = findViewById(R.id.rcv_list);
    }

    private void initData(){

        Point point = new Point();
        WindowManager windowManager = getWindowManager();
        windowManager.getDefaultDisplay().getSize(point);
        DisplayMetrics displayMetrics1 = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics1);

        Log.e("TAG",point.x+"----"+point.y);
        Log.e("TAG",""+displayMetrics1.density);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        /**
         * 根据像素分辨率，在屏幕指定物理宽高范围内能显示的像素数量
         *
         * 在同样的宽高区域，低密度的显示屏能显示的像素较少，高密度较多
         *
         *
         * 密度无关的像素DPI
         1440----2560
         4.0
         4.0----1440.0----580.571----580.571----640
         360.0

         */
        float a = metrics.density;
        float b = metrics.widthPixels;
        float c = metrics.xdpi;
        Log.e("TAG",a+"----"
                +b+"----"
                +c+"----"
                +metrics.ydpi+"----"
                +metrics.densityDpi);

        //整个屏幕dp
        float e = metrics.widthPixels / (metrics.densityDpi/DisplayMetrics.DENSITY_DEFAULT);
        Log.e("TAG",""+ e);


//        getSystemService(Context.)Context

        /**
         * LayoutManager布局管理器
         * LinearLayout 以垂直或水平滚动
         * GridLayout 在网格中显示项目
         * StaggeredGridLayout 在分散对齐网格中显示项目
         *
         */

//        mRycList.setLayoutManager(new LinearLayoutManager(this));
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
//        mRycList.setLayoutManager(gridLayoutManager);
        mRycList.setLayoutManager(staggeredGridLayoutManager);

        MyRecyclerAdapter adapter = new MyRecyclerAdapter(this);
        mRycList.setAdapter(adapter);

//        mRycList.setItemAnimator(new DefaultItemAnimator());


    }

    private void initListener(){

    }
}
