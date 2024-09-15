package com.des4025.smartsunglass.service;

import android.Manifest;
import android.os.Build;
import android.os.StrictMode;

import androidx.core.app.ActivityCompat;

import com.des4025.smartsunglass.MainActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

public class PermissionService {

    //STT 관련 필드
    private static final int PERMISSION = 1;

    public static void enforcePermission(MainActivity activity){
        AndPermission.with(activity)
                .runtime()
                .permission(
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .start();
    }

    public static void checkPermission(MainActivity activity){
        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(activity, new String[]{
                    Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO
            }, PERMISSION);
        }
    }
}
