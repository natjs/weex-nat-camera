package com.instapp.nat.weex.plugin.Camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import com.alibaba.weex.plugin.annotation.WeexModule;
import com.instapp.nat.camera.CameraModule;
import com.instapp.nat.camera.Constant;
import com.instapp.nat.camera.ModuleResultListener;
import com.instapp.nat.camera.Util;
import com.instapp.nat.permission.PermissionChecker;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Acathur on 17/2/18.
 */

@WeexModule(name = "nat/camera")
public class Camera extends WXModule {

    JSCallback mImageCallBack;
    JSCallback mVideoCallBack;

    String lang = Locale.getDefault().getLanguage();
    Boolean isChinese = lang.startsWith("zh");
    
    @JSMethod
    public void captureImage(HashMap<String, Object> param, final JSCallback jsCallback){
        boolean b = PermissionChecker.lacksPermissions(mWXSDKInstance.getContext(), Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (b) {
            HashMap<String, String> dialog = new HashMap<>();
            if (isChinese) {
                dialog.put("title", "权限申请");
                dialog.put("message", "请允许应用使用相机");
            } else {
                dialog.put("title", "Permission Request");
                dialog.put("message", "Please allow the app to use the camera");
            }
            PermissionChecker.requestPermissions((Activity) mWXSDKInstance.getContext(), dialog, new com.instapp.nat.permission.ModuleResultListener() {
                @Override
                public void onResult(Object o) {
                    if ((boolean)o == true) jsCallback.invoke(Util.getError(Constant.CAMERA_PERMISSION_DENIED, Constant.CAMERA_PERMISSION_DENIED_CODE));
                }
            }, Constant.CAMERA_PERMISSION_REQUEST_CODE,  Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            realCaptureImage(param, jsCallback);
        }
    }

    public void realCaptureImage(HashMap<String, Object> param, final JSCallback jsCallback){
        mImageCallBack = jsCallback;
        CameraModule.getInstance(mWXSDKInstance.getContext()).captureImage((Activity) mWXSDKInstance.getContext(), new ModuleResultListener() {
            @Override
            public void onResult(Object o) {
                jsCallback.invoke(o);
            }
        });
    }

    @JSMethod
    public void captureVideo(HashMap<String, Object> param, final JSCallback jsCallback){
        boolean b = PermissionChecker.lacksPermissions(mWXSDKInstance.getContext(), Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (b) {
            HashMap<String, String> dialog = new HashMap<>();
            if (isChinese) {
                dialog.put("title", "权限申请");
                dialog.put("message", "请允许应用使用相机");
            } else {
                dialog.put("title", "Permission Request");
                dialog.put("message", "Please allow the app to use the camera");
            }
            PermissionChecker.requestPermissions((Activity) mWXSDKInstance.getContext(), dialog, new com.instapp.nat.permission.ModuleResultListener() {
                @Override
                public void onResult(Object o) {
                    if ((boolean)o == true) jsCallback.invoke(Util.getError(Constant.CAMERA_PERMISSION_DENIED, Constant.CAMERA_PERMISSION_DENIED_CODE));
                }
            }, Constant.CAMERA_PERMISSION_REQUEST_CODE,  Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            realCaptureVideo(param, jsCallback);
        }
    }

    public void realCaptureVideo(HashMap<String, Object> param, final JSCallback jsCallback){
        mVideoCallBack = jsCallback;
        CameraModule.getInstance(mWXSDKInstance.getContext()).captureVideo((Activity) mWXSDKInstance.getContext(), new ModuleResultListener() {
            @Override
            public void onResult(Object o) {
                jsCallback.invoke(o);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Object o = CameraModule.getInstance(mWXSDKInstance.getContext()).onCaptureImgActivityResult(requestCode, resultCode, data);
        Object o1 = CameraModule.getInstance(mWXSDKInstance.getContext()).onCaptureVideoActivityResult(requestCode, resultCode, data);

        if (mImageCallBack != null) {
            mImageCallBack.invoke(o);
            mImageCallBack = null;
        }

        if (mVideoCallBack != null) {
            mVideoCallBack.invoke(o1);
            mVideoCallBack = null;
        }
    }
}
