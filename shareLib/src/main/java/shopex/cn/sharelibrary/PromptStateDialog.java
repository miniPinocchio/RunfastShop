package shopex.cn.sharelibrary;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;



/**
 * Created by 邓凯 on 2016/8/31.
 * 定制化-用来提示当前状态的的Dialog，订阅成功，修改密码成功，都会用到
 */
public class PromptStateDialog {
    /**
     * 上下文
     */
    private Activity context;
    private View view;
    private Dialog dialog;
    private Window dialogWindow;
    private final int   HIDE_LOADING_DIALOG = 100;
    private final int   SHOW_LOADING_DIALOG = 101;

    public View getView() {
        return view;
    }

    public PromptStateDialog(Activity context, int layout_id) {
        this(context, layout_id, 0);
    }


    public Dialog getDialog() {
        return dialog;
    }

    public Window getDialogWindow() {
        return dialogWindow;
    }
    Handler  handler=new Handler(){
     @Override
     public void handleMessage(Message msg) {
         switch (msg.what)
         {
             case HIDE_LOADING_DIALOG:
                 if (dialog!=null&&dialog.isShowing())
                 {
                     dialog.dismiss();
                 }

             break;

             case SHOW_LOADING_DIALOG:
                 if (dialog != null && dialog.isShowing()) {
                     dialog.dismiss();
                 }
                 if(context.isFinishing()){
                     return;
                 }
                 dialog.show();

             break;

         }
     }
 };

    public PromptStateDialog(Activity context, int layout_id, int style_id) {
        this.context = context;
        view = context.getLayoutInflater().inflate(layout_id, null, false);
        //new出Dialog  实例
        dialog = new Dialog(context, style_id);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogWindow = dialog.getWindow();
        //填充布局
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
    }


    /**
     * alpha 越大越透明 初始值必须设置5
     * @param width
     * @param height
     * @param alpha
     */
    public void setDialogShowWidthAndHeight(float width ,float height,float alpha)
    {
        //获取参数设置来设置Dialog 的宽高
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用

        if ((int)width==lp.WRAP_CONTENT)
        {
            lp.width =lp.WRAP_CONTENT;
        }else if ((int)width==lp.MATCH_PARENT){
            lp.width =lp.MATCH_PARENT;
        }else {
            lp.width= (int) width;
        }


        if ((int)height==lp.WRAP_CONTENT)
        {
            lp.height = lp.WRAP_CONTENT;
        }else if ((int)height==lp.MATCH_PARENT){
            lp.height = lp.MATCH_PARENT;
        }else {
            lp.height= (int) height;
        }


        if (alpha!=0)
        {
            lp.alpha=alpha;
        }

        dialogWindow.setAttributes(lp);
    }

    /**
     * 设置上下距离
     * @param dpValuewidth
     * @param dpValueheight
     * @param x
     * @param y
     */
    public void setDialogShowWidthAndHeight(float dpValuewidth ,float dpValueheight,float x,float y)
    {


        //获取参数设置来设置Dialog 的宽高
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if ((int)dpValuewidth==lp.WRAP_CONTENT)
        {
            lp.width =lp.WRAP_CONTENT;
        }else if ((int)dpValuewidth==lp.MATCH_PARENT){
            lp.width =lp.MATCH_PARENT;
        }else {
            lp.width= (int) dpValuewidth;
        }

        if ((int)dpValueheight==lp.WRAP_CONTENT)
        {
            lp.height = lp.WRAP_CONTENT;
        }else if ((int)dpValueheight==lp.MATCH_PARENT){
            lp.height = lp.MATCH_PARENT;
        }else {
            lp.height= (int) dpValueheight;
        }

        lp.x= (int) x;

        lp.y= (int) y;
        dialogWindow.setAttributes(lp);
    }
    /**dialog  显示状态*/
    public boolean dialogIsShowing() {
       return dialog.isShowing();
    }


    /**设置动画*/
    public void showDialogAnimations(@StyleRes int resId) {
        //设置动画
        dialogWindow.setWindowAnimations(resId);
    }

    /**
     * 设置dailog的位置
     * @param gravity
     */
    public  void setDialogGravity(int gravity)
    {
        //dialog 位置
        dialogWindow.setGravity(gravity);
    }
    /**
     * 设置背景颜色
     * @param resId
     */
    public void setBackgroundDrawableResourceColor(@DrawableRes int resId) {

        dialog.getWindow().setBackgroundDrawableResource(resId);
    }

    /**显示dialog*/
    public void showDialog() {
        handler.sendEmptyMessage(SHOW_LOADING_DIALOG);
    }


    /**销毁dialog*/
    public void dialogDismiss() {
        handler.sendEmptyMessage(HIDE_LOADING_DIALOG);
    }

    /**
     * 去掉阴影
     */
    public void setBackgroundDrawableResource()
    {
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}
