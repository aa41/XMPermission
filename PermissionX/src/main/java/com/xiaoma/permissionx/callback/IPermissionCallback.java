package com.xiaoma.permissionx.callback;

import java.util.List;

public interface IPermissionCallback {

    void permissionResult(List<String> grantPermissions,List<String> deniedPermissions,List<String> neverShowedPermissions);

    void error();
}
