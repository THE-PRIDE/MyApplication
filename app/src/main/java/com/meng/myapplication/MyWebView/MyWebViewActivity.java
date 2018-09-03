package com.meng.myapplication.MyWebView;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.meng.myapplication.R;

/**
 * Created by LMY on 18/7/5.
 *
 */

public class MyWebViewActivity extends AppCompatActivity {

    ImageView mImgView;
    WebView myWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_main);
        init();
    }


    private void init(){

        mImgView = findViewById(R.id.img_back);
        myWebView = findViewById(R.id.web_view);

        mImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myWebView.canGoBack()){
                    myWebView.goBack();
                } else {
                    MyWebViewActivity.this.finish();
                }
            }
        });

        String url = getIntent().getStringExtra("URL");
        myWebView.loadUrl(url);


        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();//SSL证书验证
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });


        //参数1：Android的本地对象  参数2：JS的对象
        //通过对象映射将Android中的本地对象和JS中的对象进行关联，
        // 从而实现JS调用Android的对象和方法
//        myWebView.addJavascriptInterface(new JS(),"Android");
        //JS代码可以通过反射获取Android对象，执行任意代码
        //Android4.2之后，Google规定，对被调用的函数以@java...进行注解
        //Android4.2之前，采用拦截prompt()进行漏洞修复


//        myWebView.removeJavascriptInterface("");
    }


    class JS{

        @JavascriptInterface
        public int getInt(){
            return 1;
        }
    }
}
