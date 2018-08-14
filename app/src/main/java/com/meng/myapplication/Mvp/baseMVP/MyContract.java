package com.meng.myapplication.Mvp.baseMVP;

import com.meng.myapplication.Mvp.Callback;

/**
 * Created by LMY on 18/7/6.
 *
 */

public interface MyContract {

    interface Model extends BaseModel{
        void getData(Callback callback);
    }

    interface View extends BaseView{
        void updateUI();
    }

    abstract class Presenter extends BasePresenter<View,Model>{
         abstract void request();

         void request3(){
             model.getData(new Callback() {
                 @Override
                 public void onResult(String text) {
                     view.updateUI();
                 }
             });
         }
    }
}
