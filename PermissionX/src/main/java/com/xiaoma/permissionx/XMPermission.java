package com.xiaoma.permissionx;

import android.app.Activity;

public class XMPermission {

    public static PermissionAction.Builder with(Activity activity){
        return  new PermissionAction.Builder(activity);
    }
}
