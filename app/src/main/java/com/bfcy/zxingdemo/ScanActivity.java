package com.bfcy.zxingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.zxing.client.android.fragment.CaptureFragment;
import com.google.zxing.client.android.fragment.ScanResultCallback;

public class ScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        CaptureFragment captureFragment = CaptureFragment.newInstance();
        captureFragment.setScanResultCallback(new ScanResultCallback() {
            @Override
            public void onSuccess(Bitmap mBitmap, String result) {

            }

            @Override
            public void onFailed() {

            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_scan, captureFragment).commit();
    }
}
