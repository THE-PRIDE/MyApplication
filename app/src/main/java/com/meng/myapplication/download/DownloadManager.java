package com.meng.myapplication.download;


import com.meng.myapplication.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LMY on 18/6/14.
 *
 */

public class DownloadManager {

    private static final AtomicReference<DownloadManager> INSTANCE = new AtomicReference<>();

    private HashMap<String, Call> downCalls; //用于存放各个下载请求
    private OkHttpClient mClient;

    //获取一个单例
    public static DownloadManager getInstance(){
        for(;;){
            DownloadManager current = INSTANCE.get();
            if (current != null){
                return current;
            }
            current = new DownloadManager();
            if (INSTANCE.compareAndSet(null,current)){
                return current;
            }
        }
    }

    private DownloadManager(){
        downCalls = new HashMap<>();
        mClient = new OkHttpClient();
    }


    public void download(String url,DownloadObserver downloadObserver){

        Observable.just(url)
                .filter(s -> !downCalls.containsKey(s))
                .flatMap(s -> Observable.just(createDownInfo(s)))
                .map(this::getRealFileName)
                .flatMap(downloadInfo -> Observable.create(new DownloadSubscribe(downloadInfo)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(downloadObserver);
    }

    /**
     * 取消请求
     * @param url  作为取消请求的标志位
     */
    public void cancel(String url){
        Call call = downCalls.get(url);
        if (call != null){
            call.cancel();
        }
        downCalls.remove(url);
    }

    private DownloadInfo createDownInfo(String url){
        DownloadInfo downloadInfo = new DownloadInfo(url);
        long contentLength = getContentLength(url);
        downloadInfo.setTotal(contentLength);
        String fileName = url.substring(url.lastIndexOf("/"));
        downloadInfo.setFileName(fileName);
        return downloadInfo;
    }

    private DownloadInfo getRealFileName(DownloadInfo downloadInfo){
        String fileName = downloadInfo.getFileName();
        long downloadLength = 0,contentLength = downloadInfo.getTotal();
        File file = new File(MyApplication.sContext.getFilesDir(),fileName);
        if (file.exists()){
            downloadLength = file.length();
        }
        int i = 1;
        while (downloadLength >= contentLength){
            int doIndex = fileName.lastIndexOf(".");
            String fileNameOther;
            if (doIndex == -1){
                fileNameOther = fileName +"("+ i +")";

            }else{
                fileNameOther = fileName.substring(0,doIndex)
                        +"("+ i +")" + fileName.substring(doIndex);
            }
            File newFile = new File(MyApplication.sContext.getFilesDir(),fileNameOther);
            file = newFile;
            downloadLength = newFile.length();
            i++;
        }

        downloadInfo.setProgress(downloadLength);
        downloadInfo.setFileName(file.getName());
        return downloadInfo;

    }


    private long getContentLength(String downloadUrl){
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        try{
            Response response = mClient.newCall(request).execute();
            if (response != null && response.isSuccessful()){
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength == 0 ? DownloadInfo.TOTAL_ERROR : contentLength;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return DownloadInfo.TOTAL_ERROR;
    }


    private class DownloadSubscribe implements ObservableOnSubscribe<DownloadInfo>{

        private DownloadInfo downloadInfo;

        public DownloadSubscribe(DownloadInfo downloadInfo){
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void subscribe(ObservableEmitter<DownloadInfo> e) throws Exception {

            String url = downloadInfo.getUrl();
            long downloadLength = downloadInfo.getProgress();
            long contentLength = downloadInfo.getTotal();

            //初始化进度信息
            e.onNext(downloadInfo);

            Request request = new Request.Builder()
                    //确定下载的范围，添加此头，则服务器就可以跳过已经下载好的部分
                    .addHeader("RANGE","bytes="+downloadLength+"-"+contentLength)
                    .url(url)
                    .build();

            Call call = mClient.newCall(request);
            downCalls.put(url,call);//把这个添加到call里面，方便取消
            Response response = call.execute();

            File file = new File(MyApplication.sContext.getFilesDir(),downloadInfo.getFileName());
            InputStream is = null;
            FileOutputStream fileOutputStream = null;
            try {
                is = response.body().byteStream();
                fileOutputStream = new FileOutputStream(file,true);
                downloadInfo.setDownFlie(file);
                byte[] buffer = new byte[2048];//缓冲数组2kb
                int len;
                while ((len = is.read(buffer)) != -1){
                    fileOutputStream.write(buffer,0,len);
                    downloadLength += len;
                    downloadInfo.setProgress(downloadLength);
                    e.onNext(downloadInfo);
                }
                fileOutputStream.flush();
                downCalls.remove(url);
            }finally {
                IOUtil.closeAll();
            }
            e.onComplete();//完成
        }
    }
}
