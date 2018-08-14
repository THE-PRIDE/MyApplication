package com.meng.myapplication.MyWebView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by LMY on 18/7/5.
 *
 * WebView中，主要漏洞
 *
 * 1.任意代码执行漏洞
 *      WebView中addJavascriptInterface接口
 *      WebView内置导出的searchBoxJavaBridge对象
 *      WebView内置导出的accessibility和accessibilityTraversalObject对象
 * 2.密码明文存储漏洞
 * 3.域控制不严格漏洞
 *
 */

public class MyWebView extends WebView {


    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyWebView(Context context) {
        super(context);
    }


    @SuppressLint("JavascriptInterface")
    @Override
    public void addJavascriptInterface(Object object, String name) {
        super.addJavascriptInterface(object, name);
    }
}
