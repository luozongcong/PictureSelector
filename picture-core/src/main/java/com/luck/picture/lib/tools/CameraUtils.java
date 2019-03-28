package com.luck.picture.lib.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import com.luck.picture.lib.config.PictureConfig;

import java.io.File;

/**
 * 相机工具类
 *
 * @author XUE
 * @since 2019/3/28 13:54
 */
public final class CameraUtils {

    private CameraUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 打开相机
     *
     * @param fragment
     * @param outputCameraPath
     */
    public void startOpenCamera(Fragment fragment, String outputCameraPath) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(fragment.getContext().getPackageManager()) != null) {
            File cameraFile = PictureFileUtils.createCameraFile(fragment.getContext(),
                    PictureConfig.TYPE_IMAGE,
                    outputCameraPath, PictureFileUtils.POSTFIX);
            Uri imageUri = parUri(fragment.getContext(), cameraFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            fragment.startActivityForResult(cameraIntent, PictureConfig.REQUEST_CAMERA);
        }
    }

    /**
     * 生成uri
     *
     * @param cameraFile
     * @return
     */
    public static Uri parUri(Context context, File cameraFile) {
        Uri imageUri;
        String authority = context.getPackageName() + ".provider";
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //通过FileProvider创建一个content类型的Uri
            imageUri = FileProvider.getUriForFile(context, authority, cameraFile);
        } else {
            imageUri = Uri.fromFile(cameraFile);
        }
        return imageUri;
    }

}
