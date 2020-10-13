package com.bfcy.zxingdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.zxing.client.android.fragment.CaptureFragment;

public class ScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        CaptureFragment captureFragment = CaptureFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_scan, captureFragment).commit();
    }
}
