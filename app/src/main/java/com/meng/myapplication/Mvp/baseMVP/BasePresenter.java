package com.meng.myapplication.Mvp.baseMVP;

import android.support.annotation.UiThread;

/**
 * Created by LMY on 18/7/6.
 *
 */

public abstract class BasePresenter<V extends BaseView, M extends BaseModel> {

    protected V view;
    protected M model;

    public BasePresenter() {
        model = createModel();
    }

    /**
     * 绑定View
     * @param view
     * 添加注解是为了确保该方法运行在主线程内
     */
    @UiThread
    void attachView(V view) {
        this.view = view;
    }

    /**
     * 解绑View
     */
    @UiThread
    void detachView() {
        this.view = null;
    }

    abstract M createModel();
}
