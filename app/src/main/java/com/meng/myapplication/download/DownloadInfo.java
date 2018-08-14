package com.meng.myapplication.download;

import java.io.File;

/**
 * Created by LMY on 18/6/14.
 *
 */

public class DownloadInfo {

    public static final long TOTAL_ERROR = -1;
    private String url;
    private long total;
    private long progress;
    private String fileName;
    private File downFlie;

    public DownloadInfo(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public long getTotal(){
        return total;
    }

    public void setTotal(long total){
        this.total = total;
    }

    public long getProgress(){
        return progress;
    }

    public void setProgress(long progress){
        this.progress = progress;
    }

    public File getDownFile(){
        return downFlie;
    }

    public void setDownFlie(File file){
        this.downFlie = file;
    }

}
