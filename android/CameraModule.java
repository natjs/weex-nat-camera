package com.nat.weex;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import com.nat.camera.HLCameraModule;
import com.nat.camera.HLConstant;
import com.nat.camera.HLModuleResultListener;
import com.nat.camera.HLUtil;
import com.nat.permission.PermissionChecker;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.HashMap;

/**
 * Created by Daniel on 17/2/18.
 */

public class HLWXCameraModule extends WXModule {

    JSCallback mImageCallBack;
    JSCallback mVideoCallBack;
    @JSMethod
    public void captureImage(HashMap<String, Object> param, final JSCallback jsCallback){
        boolean b = PermissionChecker.lacksPermissions(mWXSDKInstance.getContext(), Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (b) {
            HashMap<String, String> dialog = new HashMap<>();
            dialog.put("title", "权限申请");
            dialog.put("message", "请允许拍照");
            PermissionChecker.requestPermissions((Activity) mWXSDKInstance.getContext(), dialog, new com.nat.permission.HLModuleResultListener() {
                @Override
                public void onResult(Object o) {
                    if ((boolean)o == true) jsCallback.invoke(HLUtil.getError(HLConstant.CAMERA_PERMISSION_DENIED, HLConstant.CAMERA_PERMISSION_DENIED_CODE));
                }
            }, HLConstant.CAMERA_PERMISSION_REQUEST_CODE,  Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            realCaptureImage(param, jsCallback);
        }
    }

    public void realCaptureImage(HashMap<String, Object> param, final JSCallback jsCallback){
        mImageCallBack = jsCallback;
        HLCameraModule.getInstance(mWXSDKInstance.getContext()).captureImage((Activity) mWXSDKInstance.getContext(), new HLModuleResultListener() {
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
            dialog.put("title", "权限申请");
            dialog.put("message", "请允许录像");
            PermissionChecker.requestPermissions((Activity) mWXSDKInstance.getContext(), dialog, new com.nat.permission.HLModuleResultListener() {
                @Override
                public void onResult(Object o) {
                    if ((boolean)o == true) jsCallback.invoke(HLUtil.getError(HLConstant.CAMERA_PERMISSION_DENIED, HLConstant.CAMERA_PERMISSION_DENIED_CODE));
                }
            }, HLConstant.CAMERA_PERMISSION_REQUEST_CODE,  Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            realCaptureVideo(param, jsCallback);
        }
    }

    public void realCaptureVideo(HashMap<String, Object> param, final JSCallback jsCallback){
        mVideoCallBack = jsCallback;
        HLCameraModule.getInstance(mWXSDKInstance.getContext()).captureVideo((Activity) mWXSDKInstance.getContext(), new HLModuleResultListener() {
            @Override
            public void onResult(Object o) {
                jsCallback.invoke(o);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Object o = HLCameraModule.getInstance(mWXSDKInstance.getContext()).onCaptureImgActivityResult(requestCode, resultCode, data);
        Object o1 = HLCameraModule.getInstance(mWXSDKInstance.getContext()).onCaptureVideoActivityResult(requestCode, resultCode, data);

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
