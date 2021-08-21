package com.xiaoma.xmpermission

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.xiaoma.permissionx.XMPermission
import com.xiaoma.permissionx.callback.IPermissionAllGrantedCallback
import com.xiaoma.permissionx.callback.IPermissionDeniedCallback
import com.xiaoma.permissionx.callback.IPermissionNeverShowedCallback

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            XMPermission.with(this)
                .permission(Manifest.permission.RECORD_AUDIO)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .allPermissionCallback {
                    Toast.makeText(
                        this@MainActivity,
                        "全部请求成功",
                        Toast.LENGTH_SHORT
                    ).show()
                }.deniedPermissionCallback { deniedPermission ->
                    Toast.makeText(
                        this@MainActivity,
                        "拒绝的size:" + deniedPermission?.size,
                        Toast.LENGTH_SHORT
                    ).show()
                }.neverShowedPermissionCallback { neverShowedPermissions ->
                    Toast.makeText(
                        this@MainActivity,
                        "不在显示的size:" + neverShowedPermissions?.size,
                        Toast.LENGTH_SHORT
                    ).show()
                }.request()

        }
    }
}