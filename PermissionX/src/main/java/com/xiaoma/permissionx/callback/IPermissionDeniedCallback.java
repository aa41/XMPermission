package com.xiaoma.permissionx.callback;

import java.util.List;

public interface IPermissionDeniedCallback {
    void deniedCallback(List<String> deniedPermission,List<String> neverShowedPermissions);
}
