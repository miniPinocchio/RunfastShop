package shopex.cn.sharelibrary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import cn.sharesdk.framework.ShareSDK;

public class ShareActivity extends Activity implements SharedPopupWindow.SharedStateListen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(this);
        setContentView(R.layout.activity_share);
       Button  share_bt= (Button) findViewById(R.id.share_bt);
        share_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test","===================");
                SharedPopupWindow sharedPopupWindow=new SharedPopupWindow(ShareActivity.this);
                String imageUr="http://static1.tuicool.com/images/ad/jiankongbao120.jpg";
                String title="至尊影月dfa";
                String text="上牌分享jkjdf";
                sharedPopupWindow.setSharedStateListen(ShareActivity.this);
                int type=SharedPopupWindow.SHARE_IMAGE;
                String mics="http://sc1.111ttt.com/2016/5/06/26/199261544109.mp3";
                String url="https://www.baidu.com/";

                //第一个参数可以是v，也可以v的父布局 ，第二参数是设置显示位置，最后一个参数是否显示阴影
                sharedPopupWindow.show(v.getRootView());

                //如果想显示动画 ,也可以不用设置，有个默认动画
                //sharedPopupWindow.setAnimation();
                //设置是否显示toast
                //sharedPopupWindow.setShowToast(false);
                /**用法*/
                //图文分享
                //sharedPopupWindow.setSharedDataSource(new ShareViewDataSource(title,url,text,imageUr,url,type));

                //文本分享  微信朋友圈不支持文本分享， 微信不回调（取消不会掉，分享成功回调）
                sharedPopupWindow.setSharedDataSource(new ShareViewDataSource(title,url,text,type));

                //图片分享
                sharedPopupWindow.setSharedDataSource(new ShareViewDataSource(title,null,null,imageUr,url,type));

                //sharedPopupWindow.setSharedDataSource(new ShareViewDataSource(title,url,text,imageUr,mics,url,type));
                //sharedPopupWindow.setAtFlag("@酷我音乐");

            }
        });
    }

    @Override
    public void shareState(int state) {
        Log.i("state",state+"++++");
    }
}
