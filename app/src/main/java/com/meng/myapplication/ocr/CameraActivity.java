//package com.meng.myapplication.ocr;
//
//import android.app.Activity;
//import android.graphics.Rect;
//import android.os.Vibrator;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.sinovoice.hciocrcapture.entity.OcrResult;
//import com.sinovoice.hciocrcapture.entity.OcrType;
//import com.sinovoice.hciocrcapture.ocr.HciOcrEngine;
//import com.sinovoice.hciocrcapture.view.CaptureView;
//
//import static com.sinovoice.demo.R.drawable.flash_on;
//import static com.sinovoice.hciocrcapture.entity.OcrType.BankCard;
//import static com.sinovoice.hciocrcapture.entity.OcrType.IdCard;
//import static com.sinovoice.hciocrcapture.entity.OcrType.IdcardBack;
//
///**
// * 取相识别界面示例
// */
//public class CameraActivity extends Activity {
//
//
//    private static final String TAG = CameraActivity.class.getSimpleName();
//    public static final String RECOG_RESULT = "recog_result";
//    private static final int VIBRATE_MILLISECONDS = 200;
//    private CaptureView mCaptureView;
//    private RelativeLayout mRlBorder;
//    private ImageView mImgResult;
//    private ImageButton mBtnBack;
//    private ImageButton mBtnFlash;
//    private TextView mTvRemind;
//    private boolean mFlashState = false;
//    private Vibrator mVibrator;
//    private ViewfinderView mViewfinderView;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_camera);
//
//        initView();
//
//        initFocusRect();
//
//        initVibrator();
//
//        addRecognizeCallBack();
//
//    }
//
//    private void initVibrator() {
//        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//    }
//
//    private void initView() {
//        mCaptureView = (CaptureView) findViewById(R.id.camera);
//        mRlBorder = (RelativeLayout) findViewById(R.id.rl_focus_border);
//        mViewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
//        mCaptureView.addCallback(new CaptureView.Callback() {
//            @Override
//            public void onCameraOpened(CaptureView captureView) {
//                super.onCameraOpened(captureView);
//            }
//
//            @Override
//            public void onCameraClosed(CaptureView captureView) {
//                super.onCameraClosed(captureView);
//            }
//
//        });
//
//        mImgResult = (ImageView) findViewById(R.id.img_result);
//        mBtnBack = (ImageButton) findViewById(R.id.btn_back);
//        mBtnFlash = (ImageButton) findViewById(R.id.btn_flash);
//        mBtnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleBack();
//            }
//        });
//        mBtnFlash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleFlash();
//            }
//        });
//
//        mTvRemind = (TextView) findViewById(R.id.tv_remind_msg);
//        handleRemindMsg();
//    }
//
//    private void addRecognizeCallBack() {
//        HciOcrEngine.getInstance().addCallback(new HciOcrEngine.Callback() {
//            @Override
//            public void onProcess() {
//                mVibrator.vibrate(VIBRATE_MILLISECONDS);
//            }
//
//            @Override
//            public void onSuccess(final OcrResult ocrResult) {
//                handleBack();
//            }
//
//            @Override
//            public void onFailure() {
//            }
//        });
//    }
//
//    private void handleRemindMsg() {
//        switch (HciOcrEngine.getInstance().getCurrentType()) {
//            case IdCard:
//                mTvRemind.setText("请将身份证对齐边缘拍摄");
//                break;
//            case IdcardBack:
//                mTvRemind.setText("请将身份证反面对齐边缘拍摄");
//                break;
//            case BankCard:
//                mTvRemind.setText("请将银行卡对齐边缘拍摄");
//                break;
//        }
//    }
//
//    private void handleBack() {
//        finish();
//    }
//
//    private void handleFlash() {
//        if (!mFlashState) {
//            mFlashState = true;
//            mBtnFlash.setImageResource(R.drawable.flash_on);
//            mCaptureView.setFlash(CaptureView.FLASH_TORCH);
//        } else {
//            mFlashState = false;
//            mBtnFlash.setImageResource(R.drawable.flash_off);
//            mCaptureView.setFlash(CaptureView.FLASH_OFF);
//        }
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mCaptureView.start();
//        mCaptureView.capture(getRecogRect());
//    }
//
//    @Override
//    protected void onPause() {
//        if (mFlashState) {
//            handleFlash();
//        }
//        mCaptureView.stop();
//        super.onPause();
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    private void initFocusRect() {
//        Rect framingRect = getRecogRect();
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mRlBorder.getLayoutParams();
//        if (framingRect != null) {
//            layoutParams.width = framingRect.width();
//            layoutParams.height = framingRect.height();
//            layoutParams.leftMargin = framingRect.left;
//            layoutParams.topMargin = framingRect.top;
//        }
//        mRlBorder.setLayoutParams(layoutParams);
//        mViewfinderView.setMaskRect(framingRect);
//    }
//
//    /**
//     * 用户可按卡片比例自行设置
//     *
//     * @return
//     */
//    private Rect getRecogRect() {
//        int width = getWindowManager().getDefaultDisplay().getWidth();
//        int height = getWindowManager().getDefaultDisplay().getHeight();
//
//        return new Rect((int) (width * 0.15),
//                (int) (height - 0.443 * width) / 2,
//                (int) (width * 0.85),
//                (int) (height + 0.443 * width) / 2);
//    }
//}
