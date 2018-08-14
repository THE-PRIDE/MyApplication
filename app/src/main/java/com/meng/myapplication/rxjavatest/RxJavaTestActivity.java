package com.meng.myapplication.rxjavatest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.meng.myapplication.R;

/**
 * Created by LMY on 18/7/17.
 *
 */

public class RxJavaTestActivity extends AppCompatActivity implements View.OnClickListener{

    private Button BtnSendEvent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rxjava_test);
        BtnSendEvent = findViewById(R.id.BtnSendEvent);
        BtnSendEvent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.BtnSendEvent:

                break;
            default:
                break;
        }
    }
}
