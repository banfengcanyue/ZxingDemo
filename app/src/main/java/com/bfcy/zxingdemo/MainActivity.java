package com.bfcy.zxingdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.client.android.CaptureActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goScanActivity();
            }
        });
    }

    private void requestPermission() {
        int hasPermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA);
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            //已获取权限
            Log.i("tag", "已获取权限");
        } else {
            //未获取权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }
    }

    private void goScanActivity() {
        int hasPermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA);
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            //已获取权限
            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
            startActivity(intent);
        } else {
            //未获取权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意了权限申请
                goScanActivity();
            } else {
                //用户拒绝了权限申请，建议向用户解释权限用途
                Toast.makeText(MainActivity.this, "扫描功能需要相机权限", Toast.LENGTH_LONG).show();
            }
        }

    }
}
