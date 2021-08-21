package com.xiaoma.permissionx.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.xiaoma.permissionx.PermissionAction;

import java.util.ArrayList;
import java.util.List;

public class PermissionActivity extends Activity {

    private ArrayList<String> mPermissions;

    private final int permissionRequestCode = 10101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermissions = getIntent().getStringArrayListExtra(PermissionAction.PERMISSIONS);
        if (mPermissions == null || mPermissions.isEmpty()) {
            if (PermissionAction.currentAction != null) {
                PermissionAction.currentAction.error();
            }
            finish();
            return;
        }
        String[] permissionArray = mPermissions.toArray(new String[0]);
        ActivityCompat.requestPermissions(this, permissionArray, permissionRequestCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("111111111111111111","resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("111111111111111111","pause");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       PermissionAction.currentAction.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
        finish();
    }
}
