package com.meng.myapplication.rxjavatest;



import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by LMY on 18/5/8.
 * <p>
 * 测试RxJava用法
 */

public class MyRxJavaClass {

    public static final String TAG = "MY";

    //操作符

    public static void test(Observer subscriber){

        Observable.create(new ObservableOnSubscribe<Integer>() {//第一步：初始化Observable

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {

                Log.e(TAG,"Observable emit 1" + "\n");
                e.onNext(1);
                Log.e(TAG,"Observable emit 2" + "\n");
                e.onNext(2);
                Log.e(TAG,"Observable emit 3" + "\n");
                e.onNext(3);

                e.onComplete();

                Log.e(TAG,"Observable emit 4" + "\n");
                e.onNext(4);

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {//第三步：订阅

            //第二步：初始化Observer
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {

                i++;
                if (i == 2){
                    //在RxJava 2.x中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {

                Log.e(TAG,"onError : value : " + e.getMessage()+"\n");
            }

            @Override
            public void onComplete() {
                Log.e(TAG,"onComplete" + "\n");
            }
        });
    }


    Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> e) throws Exception {

        }
    });

    public void test1(){
        observable.unsubscribeOn(Schedulers.io());
    }


}

