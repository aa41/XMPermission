package com.xiaoma.permissionx.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<String> shouldShowRequestPermissionRationales = new ArrayList<>();
        List<String> grantedPermissions = new ArrayList<>();
        List<String> deniedPermissions = new ArrayList<>();

        if (requestCode == permissionRequestCode) {
            for (int i = 0; i < permissions.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    boolean shouldShow = shouldShowRequestPermissionRationale(permissions[i]);
                    if (!shouldShow) {
                        shouldShowRequestPermissionRationales.add(permissions[i]);
                    }
                }
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.add(permissions[i]);
                } else {
                    deniedPermissions.add(permissions[i]);
                }

            }

        }
        if (PermissionAction.currentAction != null) {
            PermissionAction.currentAction.permissionResult(grantedPermissions, deniedPermissions, shouldShowRequestPermissionRationales);

            if (grantedPermissions.size() == permissions.length) {
                PermissionAction.currentAction.grantedCallback(grantedPermissions);
            } else {
                if (deniedPermissions.size() > 0) {
                    PermissionAction.currentAction.deniedCallback(deniedPermissions, shouldShowRequestPermissionRationales);
                } else if (shouldShowRequestPermissionRationales.size() > 0) {
                    PermissionAction.currentAction.neverShowedCallback(shouldShowRequestPermissionRationales);
                }

            }
            PermissionAction.currentAction = null;
        }

        finish();
    }
}
