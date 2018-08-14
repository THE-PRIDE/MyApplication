//package com.meng.myapplication.ocr;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.view.View;
//
///**
// *半透明层界面
// */
//public class ViewfinderView extends View {
//
//    private static final int MASK_COLOR = 0x50000000;
//    private Paint paint;
//    private Rect mMaskRect;
//
//    public ViewfinderView(Context context) {
//        super(context);
//    }
//
//    public ViewfinderView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//    }
//
//    public ViewfinderView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//
//    public void setMaskRect(Rect rect) {
//        mMaskRect = rect;
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        if (mMaskRect == null) {
//            return;
//        }
//        int width = canvas.getWidth();
//        int height = canvas.getHeight();
//        paint.setColor(MASK_COLOR);
//        canvas.drawRect(0, 0, width, mMaskRect.top, paint);
//        canvas.drawRect(0, mMaskRect.top, mMaskRect.left, mMaskRect.bottom + 1, paint);
//        canvas.drawRect(mMaskRect.right, mMaskRect.top, width, mMaskRect.bottom + 1, paint);
//        canvas.drawRect(0, mMaskRect.bottom + 1, width, height, paint);
//    }
//}
