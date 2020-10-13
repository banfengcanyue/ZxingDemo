package com.google.zxing.client.android.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.client.android.InactivityTimer;
import com.google.zxing.client.android.R;
import com.google.zxing.client.android.ViewfinderView;
import com.google.zxing.client.android.camera.CameraManager;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;

/**
 * 二维码扫描Fragment
 */
public class CaptureFragment extends Fragment implements SurfaceHolder.Callback {

    private InactivityTimer inactivityTimer;// 先不管
    private static final String ARG_LAYOUT_ID = "layoutId";
    private int layoutId = -1;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    private CameraManager cameraManager;
    private CaptureFragmentHandler handler;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private ViewfinderView viewfinderView;

    private CaptureFragment() {
        // Required empty public constructor
    }

    public static CaptureFragment newInstance() {
        CaptureFragment fragment = new CaptureFragment();
        return fragment;
    }

    public static CaptureFragment newInstance(int layoutId) {
        CaptureFragment fragment = new CaptureFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_ID, layoutId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            layoutId = getArguments().getInt(ARG_LAYOUT_ID);
        }
        cameraManager = new CameraManager(getActivity().getApplicationContext());
        hasSurface = false;
//        inactivityTimer = new InactivityTimer(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (layoutId == -1) {
            layoutId = R.layout.fragment_capture;
        }
        View view = inflater.inflate(layoutId, container, false);
        viewfinderView = view.findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);

        surfaceView = view.findViewById(R.id.surface_view);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setKeepScreenOn(true);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (hasSurface) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(10000);
                }
            });
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

    }

    @Override
    public void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        super.onPause();
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 10000) {
                initCamera(surfaceHolder);
            }
            return false;
        }
    });

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(10000);
                }
            });
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            if (handler == null) {
                handler = new CaptureFragmentHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 扫描结果
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        Log.i("tag", "--->handleDecode: " + rawResult.toString());
    }

    /**
     * 重新启动预览和解码
     */
    public void restartPreviewAndDecode() {
        handler.restartPreviewAndDecode();
    }


    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public CaptureFragmentHandler getHandler() {
        return handler;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

}
