package com.example.library

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import java.util.*

/**
 *@author zhengyiheng
 *on 2020-04-28
 */
typealias  PermissionCallBack = (Boolean, List<String>) -> Unit

class InvisibleFragment : Fragment() {
    //定义一个callback，作为权限申请回调
    private var callBack: PermissionCallBack? = null

    //定义申请权限的方法，vararg代表可变长度的 permissions 参数列表
    fun requestNow(cb: PermissionCallBack, vararg permissions: String) {
        this.callBack = cb
        requestPermissions(permissions, 1)
    }

    /**
     * 请求返回结果
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            // deniedList 用来记录被用户拒绝的权限
            val deniedList = ArrayList<String>()
            for ((index, result) in grantResults.withIndex()) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            //allGranted用来标识所有权限都已被授权
            val allGranted = deniedList.isEmpty()
            //对申请的权限进行回调
            callBack?.let { it(allGranted, deniedList) }
        }
    }
}