package com.meng.myapplication.download;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by LMY on 18/6/14.
 *
 */

public abstract class DownloadObserver implements Observer<DownloadInfo> {

    protected Disposable d;//可以用于取消注册的监听者
    protected DownloadInfo downloadInfo;

    @Override
    public void onSubscribe(Disposable d) {
        this.d  = d;
    }

    @Override
    public void onNext(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
