package com.meng.myapplication.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewStub;

import com.meng.myapplication.R;

/**
 * Created by LMY
 *
 */

public class DetailActivity extends AppCompatActivity {

    private ViewStub viewStub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        viewStub = findViewById(R.id.view_stub);
        viewStub.inflate();
//        viewStub.setVisibility(ViewStub.VISIBLE);
    }
}
