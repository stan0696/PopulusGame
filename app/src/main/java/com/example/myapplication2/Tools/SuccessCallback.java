package com.example.myapplication2.Tools;


/**
 * 类说明：申请权限成功的回调
 * create by liuxiong at 2019/4/28 0028 20:29.
 */
public abstract class SuccessCallback implements PermissionCallback{

    @Override
    public void shouldShowRational(String[] rationalPermissons, boolean before) {

    }

    @Override
    public void onPermissonReject(String[] rejectPermissons) {

    }
}

