//package com.meng.myapplication.ocr;
//
//import android.app.AlertDialog;
//import android.content.ContentResolver;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Vibrator;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.sinovoice.hciocrcapture.entity.OcrAccountInfo;
//import com.sinovoice.hciocrcapture.entity.OcrResult;
//import com.sinovoice.hciocrcapture.entity.OcrType;
//import com.sinovoice.hciocrcapture.ocr.HciOcrEngine;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.channels.FileChannel;
//import java.nio.charset.Charset;
//import java.nio.charset.CharsetEncoder;
//import java.nio.charset.StandardCharsets;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final int REQUEST_CODE = -1;
//    private static final int LOCAL_ALBUM = 0;
//    private static final int CAMERA_PREVIEW = 1;
//    private final static int REQUEST_IMAGE_SELECT = 2;
//    private static final int VIBRATE_MILLISECONDS = 200;
//
//    private static final String cloudUrl = "";
//    private static final String appKey = "";
//    private static final String developerKey = "";
//    private HciOcrEngine mOcrEngine;
//    private TextView mTvResult;
//    private Vibrator mVibrator;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        initViewAction();
//
//        initOcrEngine();
//
//        initVibrator();
//
//        addRecognizeCallBack();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mOcrEngine.release();
//    }
//
//    private void initVibrator() {
//        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//    }
//
//    private void initViewAction() {
//        Button btnIdCard = (Button) findViewById(R.id.btn_idcard);
//        btnIdCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseDialog(OcrType.IdCard);
//            }
//        });
//        Button btnIdCardBack = (Button) findViewById(R.id.btn_idcard_back);
//        btnIdCardBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseDialog(OcrType.IdcardBack);
//            }
//        });
//
//        Button btnBankcard = (Button) findViewById(R.id.btn_bankcard);
//        btnBankcard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chooseDialog(OcrType.BankCard);
//            }
//        });
//
//        mTvResult = (TextView) findViewById(R.id.tv_result);
//    }
//
//    private void initOcrEngine() {
//        mOcrEngine = HciOcrEngine.getInstance();
//        OcrAccountInfo accountInfo = new OcrAccountInfo(cloudUrl, developerKey, appKey);
//        //设置本地授权路径 请预先拷贝本地授权文件到指定目录下
//        accountInfo.setAuthPath(getFilesDir().getAbsolutePath());
//        mOcrEngine.init(getApplicationContext(), accountInfo);
//    }
//
//    private void addRecognizeCallBack() {
//        mOcrEngine.addCallback(new HciOcrEngine.Callback() {
//            @Override
//            public void onProcess() {
//
//            }
//
//            @Override
//            public void onSuccess(final OcrResult ocrResult) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mTvResult.setText(ocrResult.getContent());
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mTvResult.setText("识别失败");
//                    }
//                });
//            }
//        });
//    }
//
//    private void chooseDialog(final OcrType type) {
//        mOcrEngine.setRecognizeType(type);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(R.string.chooseDialog_title);
//        final String items[] = {"本地相册", "相机取像"};
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case LOCAL_ALBUM:
//                        getLocalAlbum();
//                        break;
//                    case CAMERA_PREVIEW:
//                        startRecognizeActivity();
//                        break;
//                }
//            }
//        });
//        builder.create().show();
//    }
//
//    /**
//     * 本地相册取图识别
//     */
//    private void getLocalAlbum() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, REQUEST_IMAGE_SELECT);
//    }
//
//
//    /**
//     * 相机取相识别
//     */
//    private void startRecognizeActivity() {
//        Intent intent = new Intent(MainActivity.this, CameraActivity.class);
//        startActivityForResult(intent, REQUEST_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE) {
//            if (data != null) {
//                String result = (String) data.getExtras().get(CameraActivity.RECOG_RESULT);
//            }
//        } else if (requestCode == REQUEST_IMAGE_SELECT) {
//            if (data != null) {
//                RecognizeThread recognizeThread = new RecognizeThread(data);
//                recognizeThread.start();
//                mTvResult.setText("识别中");
//            }
//        }
//    }
//
//    private void localAlbumRecog(byte[] recogData) {
//        mOcrEngine.recognize(getApplicationContext(), recogData);
//    }
//
//    private byte[] getRecogData(Intent data) {
//        Uri imageUri = data.getData();
//        ContentResolver contentResolver = getContentResolver();
//        try {
//            InputStream inputStream = contentResolver.openInputStream(imageUri);
//            ByteArrayOutputStream recogByteOut = new ByteArrayOutputStream();
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, recogByteOut);
//            return recogByteOut.toByteArray();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private class RecognizeThread extends Thread {
//        private final Intent data;
//
//        RecognizeThread(Intent data) {
//            this.data = data;
//        }
//
//        @Override
//        public void run() {
//            super.run();
//            byte[] data = getRecogData(this.data);
//            localAlbumRecog(data);
//        }
//    }
//}
