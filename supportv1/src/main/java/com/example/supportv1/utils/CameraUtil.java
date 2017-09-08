package com.example.supportv1.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;

@TargetApi(9)
public class CameraUtil
{
    /**
     * 是否支持摄像头拍照
     * 
     * @param context
     * @return boolean
     */
    public static boolean hasCameraSupport(Context context)
    {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /**
     * 是否存在前置摄像头
     * 
     * @return boolean
     */
    public static boolean hasFrontCamera()
    {
        return getCameraId(CameraInfo.CAMERA_FACING_FRONT) != -1;
    }

    /**
     * 是否存在后置摄像头
     * 
     * @return boolean
     */
    public static boolean hasBackCamera()
    {
        return getCameraId(CameraInfo.CAMERA_FACING_BACK) != -1;
    }

    /**
     * 得到摄像头个数
     * 
     * @return int
     */
    public static int getNumberOfCameras()
    {
        return Camera.getNumberOfCameras();
    }

    /**
     * 打开指定的摄像头
     * 
     * @param id
     *            摄像头ID
     * @return 摄像头对象
     */
    public static Camera openCamera(final int id)
    {
        if (getNumberOfCameras() > id)
        {
            return Camera.open(id);
        }
        else
        {
            return null;
        }

    }

    /**
     * 打开默认的摄像头
     * 
     * @return 摄像头对象
     */
    public static Camera openDefaultCamera()
    {
        if (getNumberOfCameras() > 0)
        {
            return Camera.open(0);
        }
        else
        {
            return null;
        }
    }

    /**
     * 打开前置摄像头
     * 
     * @return 摄像头对象
     */
    public static Camera openFrontCamera()
    {
        if (hasFrontCamera())
        {
            return Camera.open(getCameraId(CameraInfo.CAMERA_FACING_FRONT));
        }
        else
        {
            return null;
        }
    }

    /**
     * 打开后置摄像头
     * 
     * @return 摄像头对象
     */
    public static Camera openBackCamera()
    {
        if (hasBackCamera())
        {
            return Camera.open(getCameraId(CameraInfo.CAMERA_FACING_BACK));
        }
        else
        {
            return null;
        }

    }

    /**
     * 得到摄像头信息
     * 
     * @param cameraId
     *            摄像头ID
     * @return
     */
    public static MyCameraInfo getCameraInfo(final int cameraId)
    {
        if (Camera.getNumberOfCameras() > cameraId)
        {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(cameraId, info);
            MyCameraInfo myInfo = new MyCameraInfo();
            myInfo.facing = info.facing;
            myInfo.orientation = info.orientation;
            return myInfo;
        }
        else
        {
            return null;
        }
    }

    /**
     * 摄像头信息类
     */
    public static class MyCameraInfo
    {
        public int facing;
        public int orientation;
    }

    /**
     * 通过相机方向获取照相机id
     * 
     * @param facing 相机方向
     * @return int 照片id
     */
    public static int getCameraId(final int facing)
    {
        int numberOfCameras = Camera.getNumberOfCameras();
        CameraInfo info = new CameraInfo();
        for (int id = 0; id < numberOfCameras; id++)
        {
            Camera.getCameraInfo(id, info);
            if (info.facing == facing)
            {
                return id;
            }
        }
        return -1;
    }
}
