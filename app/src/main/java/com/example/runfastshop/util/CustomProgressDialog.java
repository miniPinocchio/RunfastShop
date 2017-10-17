package com.example.runfastshop.util;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.runfastshop.R;


/**
 * Created by Administrator on 2016/6/22.
 */
public class CustomProgressDialog extends Dialog{
    private static CustomProgressDialog progressDialog;
    private Context context = null;
    private static CustomProgressDialog customProgressDialog = null;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static CustomProgressDialog createDialog(Context context){
        customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.custom_progress_dialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus){

        if (customProgressDialog == null){
            return;
        }

        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        //1、加载
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.progress_round);
        //3、给动画设置插直器
        animation.setInterpolator(new LinearInterpolator());
        //2、播放
        imageView.startAnimation(animation);
    }

    /**
     *
     * [Summary]
     *       setTitile 标题
     * @param strTitle
     * @return
     *
     */
    public CustomProgressDialog setTitile(String strTitle){
        return customProgressDialog;
    }

    /**
     *
     * [Summary]
     *       setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public CustomProgressDialog setMessage(String strMessage){

        return customProgressDialog;
    }

    public static void setDialog(AlertDialog dialog){
        Window win = dialog.getWindow();
        //win.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    /**
     * 开始动画
     */
    public static void startProgressDialog(Context context){
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(context);
            //progressDialog.setCancelable(false);
            progressDialog.setMessage("正在登录...");
        }
        progressDialog.show();
    }

    /**
     * 结束动画
     */
    public static void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
