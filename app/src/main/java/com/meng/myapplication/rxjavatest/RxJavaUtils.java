package com.meng.myapplication.rxjavatest;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by LMY on 18/7/17.
 *
 */

public class RxJavaUtils {

    public static Observable getObservable(){
        return Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {

            }
        });
    }


}
