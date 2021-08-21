package com.xiaoma.permissionx;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.xiaoma.permissionx.activity.PermissionActivity;
import com.xiaoma.permissionx.callback.IPermissionAllGrantedCallback;
import com.xiaoma.permissionx.callback.IPermissionCallback;
import com.xiaoma.permissionx.callback.IPermissionDeniedCallback;
import com.xiaoma.permissionx.callback.IPermissionNeverShowedCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PermissionAction implements IPermissionCallback,IPermissionNeverShowedCallback,IPermissionAllGrantedCallback,IPermissionDeniedCallback
{
    public static final String PERMISSIONS = "permissions";

    private WeakReference<Activity> actRef;
    private ArrayList<String> permissions;
    private ArrayList<IPermissionCallback> callbacks;
    private ArrayList<IPermissionDeniedCallback> deniedCallbacks;
    private ArrayList<IPermissionAllGrantedCallback> grantedCallbacks;
    private ArrayList<IPermissionNeverShowedCallback> neverShowedCallbacks;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    public static PermissionAction currentAction;


    public void openPermissionSettings(){
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (actRef != null) {
                    Activity targetAct = actRef.get();
                    if (targetAct != null && !(targetAct instanceof PermissionActivity) && permissions != null && !permissions.isEmpty()) {
                       //todo
                        /*Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                         intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                                         startActivity(intent);*/
                    }
                }
            }
        });
    }


    public void request() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (actRef != null) {
                    Activity targetAct = actRef.get();
                    if (targetAct != null && !(targetAct instanceof PermissionActivity) && permissions != null && !permissions.isEmpty()) {
                        currentAction = PermissionAction.this;
                        Intent intent = new Intent(targetAct, PermissionActivity.class);
                        intent.putStringArrayListExtra(PERMISSIONS, permissions);
                        targetAct.startActivity(intent);
                    }
                }
            }
        });


    }

    @Override
    public void permissionResult(List<String> grantPermissions, List<String> deniedPermissions, List<String> neverShowedPermissions) {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (IPermissionCallback callback : callbacks) {
                callback.permissionResult(grantPermissions, deniedPermissions, neverShowedPermissions);
            }
        }
    }

    @Override
    public void error() {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (IPermissionCallback callback : callbacks) {
                callback.error();
            }
        }
    }

    @Override
    public void grantedCallback(List<String> grantedPermissions) {
        if (grantedCallbacks != null && !grantedCallbacks.isEmpty()) {
            for (IPermissionAllGrantedCallback callback : grantedCallbacks) {
                callback.grantedCallback(grantedPermissions);
            }
        }
    }

    @Override
    public void deniedCallback(List<String> deniedPermissions,List<String> neverShowedPermissions) {
        if (deniedCallbacks != null && !deniedCallbacks.isEmpty()) {
            for (IPermissionDeniedCallback callback : deniedCallbacks) {
                callback.deniedCallback(deniedPermissions,neverShowedPermissions);
            }
        }
    }

    @Override
    public void neverShowedCallback(List<String> neverShowedPermissions) {
        if (neverShowedCallbacks != null && !neverShowedCallbacks.isEmpty()) {
            for (IPermissionNeverShowedCallback callback : neverShowedCallbacks) {
                callback.neverShowedCallback(neverShowedPermissions);
            }
        }
    }

    public static class Builder {

        private final WeakReference<Activity> actRef;

        private final ArrayList<String> permissions = new ArrayList<>();

        private final ArrayList<IPermissionCallback> callbacks = new ArrayList<>();
        private final ArrayList<IPermissionDeniedCallback> deniedCallbacks = new ArrayList<>();
        private final ArrayList<IPermissionAllGrantedCallback> grantedCallbacks = new ArrayList<>();
        private final ArrayList<IPermissionNeverShowedCallback> neverShowedCallbacks = new ArrayList<>();

        public Builder(Activity activity) {
            actRef = new WeakReference<>(activity);
        }

        public Builder permission(String permission) {
            if (!TextUtils.isEmpty(permission) && !permissions.contains(permission)) {
                permissions.add(permission);
            }
            return this;
        }

        public Builder callback(IPermissionCallback callback) {
            if (callback != null && !callbacks.contains(callback)) {
                callbacks.add(callback);
            }
            return this;
        }

        public Builder deniedPermissionCallback(IPermissionDeniedCallback callback) {
            if (callback != null && !deniedCallbacks.contains(callback)) {
                deniedCallbacks.add(callback);
            }
            return this;
        }

        public Builder allPermissionCallback(IPermissionAllGrantedCallback callback) {
            if (callback != null && !grantedCallbacks.contains(callback)) {
                grantedCallbacks.add(callback);
            }
            return this;
        }


        public Builder neverShowedPermissionCallback(IPermissionNeverShowedCallback callback) {
            if (callback != null && !neverShowedCallbacks.contains(callback)) {
                neverShowedCallbacks.add(callback);
            }
            return this;
        }

        public PermissionAction request() {
            PermissionAction permissionAction = new PermissionAction();
            permissionAction.actRef = actRef;
            permissionAction.permissions = permissions;
            permissionAction.callbacks = callbacks;
            permissionAction.grantedCallbacks = grantedCallbacks;
            permissionAction.deniedCallbacks = deniedCallbacks;
            permissionAction.neverShowedCallbacks = neverShowedCallbacks;
            permissionAction.request();
            return permissionAction;
        }


    }

}
