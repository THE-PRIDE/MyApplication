package com.meng.myapplication.MyWebView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;

/**
 * Created by LMY on 18/7/5.
 *
 */

public class MyWebViewActivity extends AppCompatActivity {

    MyWebView myWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    private void init(){
        //参数1：Android的本地对象  参数2：JS的对象
        //通过对象映射将Android中的本地对象和JS中的对象进行关联，
        // 从而实现JS调用Android的对象和方法
        myWebView.addJavascriptInterface(new JS(),"Android");
        //JS代码可以通过反射获取Android对象，执行任意代码
        //Android4.2之后，Google规定，对被调用的函数以@java...进行注解
        //Android4.2之前，采用拦截prompt()进行漏洞修复


        myWebView.removeJavascriptInterface("");
    }


    class JS{

        @JavascriptInterface
        public int getInt(){
            return 1;
        }
    }
}
