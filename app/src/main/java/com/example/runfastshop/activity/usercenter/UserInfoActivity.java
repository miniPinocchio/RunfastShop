package com.example.runfastshop.activity.usercenter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.NetConfig;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.data.IntentFlag;
import com.example.runfastshop.impl.constant.UrlConstant;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.PhotoUtils;
import com.example.supportv1.utils.FileUtil;
import com.example.supportv1.utils.LogUtil;
import com.example.supportv1.utils.PermissionUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

import static com.example.runfastshop.util.PhotoUtils.hasSdcard;

/**
 * 个人信息
 */
public class UserInfoActivity extends ToolBarActivity implements View.OnClickListener, retrofit2.Callback<String> {

    @BindView(R.id.tv_address_button)
    TextView tvAddressButton;
    @BindView(R.id.tv_user_nickname)
    TextView tvUserNickname;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.tv_user_email)
    TextView tvUserEmail;
    @BindView(R.id.tv_update_password)
    TextView tvUpdatePassword;
    @BindView(R.id.ll_nickname)
    LinearLayout mLlNickname;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    private User userInfo;
    //更新头像所用
    private AlertDialog dialogImage;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
            + "uploadImage.jpg");

    private String saveImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "wlPicture" + File.separator
            + "headPic.jpg";

    private static final int PHOTO = 2;
    private static final int CAMERA = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        showContacts();
        updateUi();
        initPhotoSelect();
    }

    private void updateUi() {
        if (!TextUtils.isEmpty(userInfo.getPic())){
            x.image().bind(ivHead, UrlConstant.ImageHeadBaseUrl+userInfo.getPic(), NetConfig.optionsHeadImage);
        }
        tvUserNickname.setText(TextUtils.isEmpty(userInfo.getNickname()) ? userInfo.getMobile() : userInfo.getNickname());
        tvUserPhone.setText(TextUtils.isEmpty(userInfo.getMobile()) ? "添加" : userInfo.getMobile());
        tvUserEmail.setText(TextUtils.isEmpty(userInfo.getEmail()) ? "添加" : userInfo.getEmail());
    }

    /**
     * 手动获取权限
     */
    public void showContacts() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA}, 100);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    /**
     * 初始化头像更新选择框
     */
    private void initPhotoSelect() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_select_head, null);
        TextView tvPhotoType = (TextView) view.findViewById(R.id.tv_photo_type);
        tvPhotoType.setText("请选择更换头像方式");
        TextView tvCamera = (TextView) view.findViewById(R.id.tv_select_camera);
        tvCamera.setText("照相机更换");
        tvCamera.setOnClickListener(this);
        TextView tvPhoto = (TextView) view.findViewById(R.id.tv_select_photo);
        tvPhoto.setText("照片更换");
        tvPhoto.setOnClickListener(this);
        dialogImage = new AlertDialog.Builder(this).setView(view).create();
    }

    @OnClick({R.id.tv_address_button, R.id.tv_user_email, R.id.tv_update_password,
            R.id.layout_exit, R.id.ll_nickname, R.id.layout_update_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_update_head:
                Window window = dialogImage.getWindow();
                if (window != null) {
                    window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
                    window.setWindowAnimations(R.style.dialogAnim);  //添加动画
                    dialogImage.show();
                }
                break;
            case R.id.tv_address_button://地址管理
                Intent intent = new Intent(this, AddressSelectActivity.class);
                intent.setFlags(IntentFlag.MANAGER_ADDRESS);
                startActivity(intent);
                break;
            case R.id.tv_update_password://密码管理
                startActivity(new Intent(this, UpdatePasswordActivity.class));
                break;
            case R.id.layout_exit://
                exitLogin();
                break;
            case R.id.ll_nickname://昵称
                intent = new Intent(this, ChangeNameActivity.class);
                intent.setFlags(IntentFlag.EDIT_NICKNAME);
                intent.putExtra("userInfo", userInfo);
                startActivityForResult(intent, 1002);
                break;
            case R.id.tv_user_email://邮箱
                intent = new Intent(this, ChangeNameActivity.class);
                intent.setFlags(IntentFlag.EDIT_EMAIL);
                intent.putExtra("userInfo", userInfo);
                startActivityForResult(intent, 1003);
                break;
        }
    }


    // 是否确定退出登录
    private void exitLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.prompt));
        builder.setMessage("确定退出登录？");
        String ok = getResources().getString(R.string.update_tip_confrim);
        builder.setPositiveButton(ok, new ExitLoginImpl());
        builder.setNegativeButton(getString(R.string.cancel), null);
        Dialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.tv_select_camera:
                    //检查拍照权限
                    if (PermissionUtils.checkCameraScanPermission(this)) {
                        autoObtainCameraPermission();
                    } else {
                        CustomToast.INSTANCE.showToast(this, "未开启拍照权限");
                    }
                    break;
                case R.id.tv_select_photo:
                    if (PermissionUtils.checkExternalStoragePermission(this)) {
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, PHOTO);
                    } else {
                        CustomToast.INSTANCE.showToast(this, "未开启文件访问权限");
                    }
                    break;
            }
        }
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (hasSdcard()) {
            imageUri = Uri.fromFile(fileUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(this, "com.example.runfastshop.FileProvider", fileUri);//通过FileProvider创建一个content类型的Uri
            }
            PhotoUtils.takePicture(this, imageUri, CAMERA);
        } else {
            CustomToast.INSTANCE.showToast(this, "设备没有SD卡！");
        }
    }

    /**
     * 退出登录
     */
    private class ExitLoginImpl implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            UserService.setAutoLogin("0");
            UserService.clearUserInfo();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            initData();
        } else if (requestCode == 1002 && resultCode == RESULT_OK) {
            initData();
        } else if (requestCode == 1003 && resultCode == RESULT_OK) {
            initData();
        }
        if (dialogImage != null) {
            dialogImage.dismiss();
        }
        switch (requestCode) {
            case CAMERA:
                if (resultCode != Activity.RESULT_OK) {
                    return;
                }
                if (!Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    CustomToast.INSTANCE.showToast(this, "SD不可用");
                    return;
                }
                //File file = new File(mImagePath);
                //Uri uri = Uri.fromFile(file);
                Uri uri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(this, "com.example.runfastshop.FileProvider", fileUri);//通过FileProvider创建一个content类型的Uri
                }
                startImageAction(uri, 200, 200, 3, true);
                //uploadImage(mImagePath);
                break;
            case PHOTO:
                Uri uriPhoto = null;
                if (data == null) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        CustomToast.INSTANCE.showToast(this, "SD不可用");
                        return;
                    }
                    if (data.getData() == null) {
                        return;
                    }
                    uriPhoto = data.getData();
                    //uploadImage(uriPhoto.getPath());
                    Log.d("filePath", "avatar - uriPhoto = " + uriPhoto);
                    startImageAction(uriPhoto, 200, 200, 3, true);
                } else {
                    CustomToast.INSTANCE.showToast(this, "照片获取失败");
                }
                break;
            case 3:
                if (data == null) {
                    return;
                } else {
                    Log.d("filePath", "avatar - bitmap = 3");
                    saveCropAvator(data, 130);
                }
                break;
        }
    }

    private void startImageAction(Uri uri, int outputX, int outputY,
                                  int requestCode, boolean isCrop) {
        Intent intent = null;
        if (isCrop) {
            intent = new Intent("com.android.camera.action.CROP");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        Log.d("filePath", "avatar - bitmap = intent");
        startActivityForResult(intent, requestCode);
    }

    /**
     * 将图片变为圆角
     *
     * @param bitmap 原Bitmap图片
     * @param pixels 图片圆角的弧度(单位:像素(px))
     * @return 带有圆角的图片(Bitmap 类型)
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 保存裁剪的头像
     *
     * @param data
     */
    private void saveCropAvator(Intent data, int rectF) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            Log.d("life", "avatar - bitmap = " + bitmap);
            if (bitmap != null) {
                bitmap = toRoundCorner(bitmap, rectF);//调用圆角处理方法
                //存储头像图片到本地
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Log.d("filePath", "avatar - bitmap = " + bitmap);
                if (FileUtil.writeFile(saveImagePath, byteArray)) {
                    Log.d("filePath", "filePath = " + saveImagePath);
                    uploadImage(saveImagePath);
                }
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
    }

    /**
     * 上传图片
     */
    private void uploadImage(String path) {
        Log.d("params", "path = " + path);
        File file = new File(path);
        if (!file.exists()) {
            CustomToast.INSTANCE.showToast(this, "文件不存在");
            return;
        }
        CustomToast.INSTANCE.showToast(this, "文件正在上传");
        RequestParams uploadParams = new RequestParams(UrlConstant.UPLOAD_PIC);
        uploadParams.setAsJsonContent(true);
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("upfile", file));
        list.add(new KeyValue("json", "1"));
        MultipartBody body = new MultipartBody(list, "UTF-8");
        uploadParams.setRequestBody(body);
        Log.d("params", "uploadParams = " + uploadParams.getUri());
        x.http().post(uploadParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("params", "params = " + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String imagePath = jsonObject.optString("filePath");
                    userInfo.setPic(imagePath);
                    updateUserInfo(imagePath);
                    UserService.saveUserInfo(userInfo);
                    x.image().bind(ivHead,UrlConstant.ImageHeadBaseUrl+imagePath, NetConfig.optionsHeadImage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CustomToast.INSTANCE.showToast(UserInfoActivity.this, "上传失败 ");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 上传头像
     * @param imagePath
     */
    private void updateUserInfo(String imagePath) {
        CustomApplication.getRetrofit().updateHead(userInfo.getId(),imagePath).enqueue(this);
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            Log.d("params","response ="+data);
            ResolveData(data);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        CustomToast.INSTANCE.showToast(this, "网络错误");
    }

    private void ResolveData(String data) {
        LogUtil.d("修改", data);
        JSONObject object = null;
        try {
            object = new JSONObject(data);
            boolean success = object.optBoolean("success");
            if (success) {
                CustomToast.INSTANCE.showToast(this, "更换成功");
            } else {
                CustomToast.INSTANCE.showToast(this, "保存失败");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
