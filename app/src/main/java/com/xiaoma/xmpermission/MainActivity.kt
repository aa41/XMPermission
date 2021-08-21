package com.xiaoma.xmpermission

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.xiaoma.permissionx.PermissionAction
import com.xiaoma.permissionx.XMPermission
import com.xiaoma.permissionx.callback.IPermissionAllGrantedCallback
import com.xiaoma.permissionx.callback.IPermissionDeniedCallback
import com.xiaoma.permissionx.callback.IPermissionNeverShowedCallback
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var permissionAction:PermissionAction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            permissionAction =  XMPermission.with(this)
                .permission(Manifest.permission.RECORD_AUDIO)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .allPermissionCallback {
                    Toast.makeText(
                        this@MainActivity,
                        "全部请求成功",
                        Toast.LENGTH_SHORT
                    ).show()
                }.deniedPermissionCallback { deniedPermission,_ ->
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
       // permissionAction?.onRequestPermissionsResult(this,requestCode,permissions,grantResults)
    }

    override fun onResume() {
        super.onResume()
        Log.e("11111111","main resume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("11111111","main pause")

    }
}