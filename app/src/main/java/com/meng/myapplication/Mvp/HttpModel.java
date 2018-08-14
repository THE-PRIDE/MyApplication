package com.meng.myapplication.Mvp;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

/**
 * Created by LMY on 18/7/2.
 *
 */

public class HttpModel {

    private Callback callback;

    public HttpModel(Callback callback){
        this.callback = callback;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            callback.onResult((String) msg.obj);
        }
    };

    public void request(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Message msg = handler.obtainMessage();
                    msg.obj = "data";
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
