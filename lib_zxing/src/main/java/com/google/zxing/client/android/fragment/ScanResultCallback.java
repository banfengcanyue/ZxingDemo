package com.google.zxing.client.android.fragment;

import android.graphics.Bitmap;

public interface ScanResultCallback {
    void onSuccess(Bitmap mBitmap, String result);
    void onFailed();
}
