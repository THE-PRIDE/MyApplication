package com.meng.myapplication.Mvp;

/**
 * Created by LMY on 18/7/2.
 *
 */

public class Presenter {
    private MVPView mvpView;
    private HttpModel httpModel;

    public Presenter(MVPView mvpView){
        this.mvpView = mvpView;
        httpModel = new HttpModel(new Callback() {
            @Override
            public void onResult(String text) {
                Presenter.this.mvpView.updateTv(text);
            }
        });
    }

    public void request(){
        httpModel.request();
    }


    //用于解决内存泄露
    public void detachView(){

        mvpView = null;
    }
}
