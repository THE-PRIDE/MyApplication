package com.meng.myapplication.glidetest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gifbitmap.GifBitmapWrapper;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.meng.myapplication.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by LMY on 18/8/9.
 *
 */

public class ImageViewActivity extends AppCompatActivity {

    private ImageView mImgGlideView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_imageview);

        String url = getIntent().getStringExtra("URL");

        mImgGlideView = findViewById(R.id.img_glide_view);
        Glide.with(this).load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Toast.makeText(ImageViewActivity.this,"fail",Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                Toast.makeText(ImageViewActivity.this,"success",Toast.LENGTH_SHORT).show();
                return false;
            }
        }).into(mImgGlideView);
    }
}
