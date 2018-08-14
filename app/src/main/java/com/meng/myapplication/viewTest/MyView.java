package com.meng.myapplication.viewTest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by LMY on 18/6/22.
 *
 */

public class MyView extends View {

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        event.getRawX();
        event.getX();

        //滑动的最小距离，跟设备有关，常量，小于该值，系统认为没有滑动
        int min = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);


        return super.onTouchEvent(event);
    }
}
