package shopex.cn.sharelibrary;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import sharesdk.onekeyshare.OnekeyShare;
import shopex.cn.sharelibrary.utils.Util;

/**
 * Created by p on 2016/10/14.
 */
public class SharedView  implements PlatformActionListener, Handler.Callback, AdapterView.OnItemClickListener{
    private Activity context;
    private  ShareViewDataSource dataSource;

    //文本
    public static   int SHARE_TEXT=0;

    //图文
    public static int SHARE_TEXT_IMAGE=1;

    //音乐
    public static int SHARE_MUSIC=2;

    //视频
    public static int SHARE_VIDEO=3;

    //网页
    public static int SHARE_WEBPAGE=4;

    //图片
    public static int SHARE_IMAGE=5;

    //用于新浪微博@官方微博
    public  String  atFlag="";

    // sdcard中的图片名称
    public static final String FILE_NAME = "/share_pic.jpg";
    public static String TEST_IMAGE;

    //分享状态回调
    private SharedStateListen  stateListen;
    private String title;
    private String titleUrl;
    private String text;
    private String site;
    private String siteUrl;
    private String imageUrl;
    private String imagePath;
    private String musicUrl;
    private String url;
    private ClipboardManager clipboard;
    private int  animation;
    private boolean isShowToast=true;
    //项目中会需要一些int类型的默认值，所以集中抽取
    private int DEFAULT_VALUE=0;


    //分享成功
    public  static final int SHARESUCCESS = 6;
    //分享失败
    public static final int SHAREFAIL = 7;
    //分享取消
    public static final int SHARECANCEL= 8;
    private final PromptStateDialog dialog;

    public SharedView(Activity context) {
        this.context=context;
        /** 注册平台信息*/
        SharedInitRegistInfo initRegistInfo=new SharedInitRegistInfo(context);

        dialog = new PromptStateDialog(context, R.layout.share_layout);
        GridView gridView = (GridView) dialog.getView().findViewById(R.id.share_gr);
        ShareAdapter adapter = new ShareAdapter(context);
        gridView.setAdapter(adapter);
        Button btn_cancel = (Button) dialog.getView().findViewById(R.id.btn_cancel);


        WindowManager.LayoutParams windowparams = dialog.getDialogWindow().getAttributes();
        dialog.setDialogShowWidthAndHeight(windowparams.MATCH_PARENT, windowparams.WRAP_CONTENT,0);
        dialog.setBackgroundDrawableResource();
        gridView.setOnItemClickListener(this);
        // 取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dialog.dialogDismiss();
            }
        });
    }

    //设置动画
    public void  setAnimation(int  animation)
    {
        this.animation =animation;
    }
    /**用来设置视图显示的位置
     * view  这个值可以是当前的view，也可以的父的view
     * */


    public void show()
    {
        show(0);
    }

    public  void  show(int gravity)
    {
        //外部设置了动画就设置动画，没有就设置一个默认的
        if (this.animation==DEFAULT_VALUE)
        {
            dialog.showDialogAnimations(R.style.AnimBottom);

        }else {
            dialog.showDialogAnimations(animation);
        }


        if (gravity==DEFAULT_VALUE)
        {
            dialog.setDialogGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL);
        }else {
            dialog.setDialogGravity(gravity);
        }

        dialog.showDialog();
    }
    /**
     * PopupWindow  dismiss
     */
    public void dismiss() {
        dialog.dialogDismiss();
    }

    /**
     * 根据提供网络路径来下载到本地,保存
     * @param imagePath
     */
    private void initImagePath(String imagePath) {
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState())
                    && Environment.getExternalStorageDirectory().exists()) {
                TEST_IMAGE = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + FILE_NAME;
            } else {
                TEST_IMAGE = context.getApplication().getFilesDir().getAbsolutePath()
                        + FILE_NAME;
            }
            // 创建图片文件夹
            File file = new File(TEST_IMAGE);
            if (!file.exists()) {
                file.createNewFile();
                //取得HttpClient 对象
                Bitmap pic = createImagePathBitmap(imagePath);
                FileOutputStream fos = new FileOutputStream(file);
                pic.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            TEST_IMAGE = null;
        }
    }

    /**
     * 构建网络对象来下载图片
     * @param imagePath
     * @return
     */
    private Bitmap  createImagePathBitmap(String imagePath)
    {
        //httpGet连接对象
        HttpGet httpRequest = new HttpGet(imagePath);
        //取得HttpClient 对象
        HttpClient httpclient = new DefaultHttpClient();
        try {
            //请求httpClient ，取得HttpRestponse
            HttpResponse httpResponse = httpclient.execute(httpRequest);
            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                //取得相关信息 取得HttpEntiy
                HttpEntity httpEntity = httpResponse.getEntity();
                //获得一个输入流
                InputStream is = httpEntity.getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     *
     * @param dataSource  设置分享数据data
     */
    public void setSharedDataSource(ShareViewDataSource dataSource)
    {
        this.dataSource=dataSource;
    }

    /**
     *
     * 设置分享状态监听
     * @param stateListen
     */
    public void setSharedStateListen(SharedStateListen stateListen)
    {
        this.stateListen=stateListen;
    }

    /**
     * 设置字段@ 值
     * @param strAtFlag
     */
    public void setAtFlag(String strAtFlag)
    {
        this.atFlag=strAtFlag;
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        //不能直接Taost，此时在子线程
        Message msg = new Message();
        msg.arg1 = SHARESUCCESS;
        msg.arg2 = i;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
        Log.i("onComplete","onComplete"+"++++++++++++");
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        //不能直接Taost，此时在子线程
        throwable.printStackTrace();
        Message msg = new Message();
        msg.arg1 = SHAREFAIL;
        msg.arg2 = i;
        msg.obj = platform;
        throwable.printStackTrace();
        UIHandler.sendMessage(msg, this);

    }

    @Override
    public void onCancel(Platform platform, int i) {
        //不能直接Taost，此时在子线程
        Message msg = new Message();
        msg.arg1 = SHARECANCEL;
        msg.arg2 = i;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
        Log.i("onCancel","onCancel"+"++++++++++++");
    }
    public boolean handleMessage(Message msg) {

        switch (msg.arg1) {
            // 1代表分享成功，2代表分享失败，3代表分享取消
            case SHARESUCCESS:
                // 成功
                //调用接口回调，返回状态
                stateListen.shareState(SHARESUCCESS);
                if (isShowToast)
                {
                    Util.alert(context,"分享成功");
                }
                break;
            case SHAREFAIL:
                //调用接口回调，返回状态
                stateListen.shareState(SHAREFAIL);
                if (isShowToast)
                {
                    Util.alert(context,"分享失败");
                }

                break;
            case SHARECANCEL:
                //调用接口回调，返回状态
                stateListen.shareState(SHARECANCEL);
                if (isShowToast)
                {
                    Util.alert(context,"分享取消");
                }
                break;

        }
        return false;
    }
    Platform platform = null;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i("position",position+"++++++++++++++++++++");

        if (dataSource==null)
        {
            Util.alert(context,"请检查ShareViewDataSource是否赋值");
            Util.LogUtils("dataSource",dataSource.toString());
            return;
        }
        Util.LogUtils("dataSource",dataSource.toString());

        title = dataSource.getShareTitle();
        titleUrl = dataSource.getShareTitleUrl();
        text = dataSource.getShareText();
        site = dataSource.getShareSite();
        siteUrl = dataSource.getShareSiteUrl();
        imageUrl = dataSource.getShareImageUrl();
        //参数ShareImagePath  可要可不要，在qq分享的时候没用到，在微信中只要设置ShareImageUrl就可以，只会耗时点
        imagePath = dataSource.getShareImagePath();
        musicUrl = dataSource.getShareMusicUrl();
        url = dataSource.getShareUrl();
        /**
         * imagePath 不等于空时
         * 开启一个线程去将网络图片下载到本，然后保存到本地分享
         */
        if (imagePath !=null) {
            new Thread() {
                public void run() {
                    initImagePath(imagePath);
                }
            }.start();
        }
        switch (position)
        {
            case 0:
                //微信朋友细节
                wechatMomentsShareDetails();
                break;
            case 1:
                //微信分享
                wechatShareDetails();
                break;
            case 2:
                //qq空间分享
                QzoneShareDetails();
                break;
            case 3:
                //qq好友
                QQFriendShareDetails();
                break;
            case 4:
                //微博分享细节
                sinaWeiboShareDetails();
                break;
            case 5:
                //复制链接
                copyURlShareDetails();
                break;
            default:
                break;
        }

        dismiss();

    }




    /**
     * 微信朋友圈分享细节
     */
    private void wechatMomentsShareDetails() {
        platform = ShareSDK.getPlatform(context, WechatMoments.NAME);
        WechatMoments.ShareParams paramsWE = new WechatMoments.ShareParams();

        if (!isWeixinAvilible(context))
        {
            Toast.makeText(context,"您的微信还未安装呢！",Toast.LENGTH_SHORT).show();
        }
        /**
         * 微信朋友圈不支持文本分享
         */
        /**
         * 文本分享必传参数Type，title，text
         */
        if (dataSource.getShareType()==SHARE_TEXT)
        {
            //文本分享必传参数
            paramsWE.setShareType(Platform.SHARE_TEXT);
        }
        /**
         * 图片分享必传参数Type，title,imageUrl
         */
        if (dataSource.getShareType()==SHARE_IMAGE)
        {
            //图片分享必传参数
            paramsWE.setShareType(Platform.SHARE_IMAGE);
            paramsWE.setImageUrl(imageUrl);

        }
        /**
         * 音乐分享必传参数Type，title，text，imageUrl，musicUrl，url
         *
         * 分享网页or图文，必传参数Type，title，text，imageUrl，musicUrl，url
         */
        if (dataSource.getShareType()==SHARE_MUSIC||dataSource.getShareType()==SHARE_TEXT_IMAGE)
        {
            if (musicUrl !=null)
            {
                paramsWE.setShareType(Platform.SHARE_MUSIC);
                paramsWE.setMusicUrl(musicUrl);
            }else {
                paramsWE.setShareType(Platform.SHARE_WEBPAGE);
            }
            paramsWE.setImageUrl(imageUrl);
            paramsWE.setUrl(url);

        }
        paramsWE.setTitle(title);
        platform.setPlatformActionListener(this);
        platform.share(paramsWE);
    }

    /**
     * 微信分享细节
     */
    private void wechatShareDetails() {
        platform = ShareSDK.getPlatform(context, Wechat.NAME);
        Wechat.ShareParams params = new Wechat.ShareParams();
        //判读是否存在客户端
        if (!isWeixinAvilible(context))
        {
            Toast.makeText(context,"您的微信还未安装呢！",Toast.LENGTH_SHORT).show();
        }
        /**
         * 文本分享必传参数Type，title，text
         */
        if (dataSource.getShareType()==SHARE_TEXT)
        {
            //文本分享必传参数
            params.setShareType(Platform.SHARE_TEXT);
        }
        /**
         * 图片分享必传参数Type，title,imageUrl
         */
        if (dataSource.getShareType()==SHARE_IMAGE)
        {
            //图片分享必传参数
            params.setShareType(Platform.SHARE_IMAGE);
            params.setImageUrl(imageUrl);

        }
        /**
         * 音乐分享必传参数Type，title，text，imageUrl，musicUrl，url
         *
         * 分享网页or图文，必传参数Type，title，text，imageUrl，musicUrl，url
         */
        if (dataSource.getShareType()==SHARE_MUSIC||dataSource.getShareType()==SHARE_TEXT_IMAGE)
        {
            if (musicUrl !=null)
            {
                params.setShareType(Platform.SHARE_MUSIC);
                params.setMusicUrl(musicUrl);
            }else {
                params.setShareType(Platform.SHARE_WEBPAGE);
            }
            params.setImageUrl(imageUrl);
            params.setUrl(url);

        }
        params.setTitle(title);
        params.setText(text);
        platform.setPlatformActionListener(this);
        platform.share(params);
    }


    /**
     * QQ空间分享细节
     */
    /**
     * 文本分享qq空间分享必须字段，title，titleUrl，text，site，siteUrl,测试site，siteUrl可以不传，空间才会用到字段
     * 文本分享给好友也可以，只是有像图文分享那样
     *
     * qq好友or空间，图文分享必传参数，title，titleUrl，text，imageUrl，site，siteUrl,
     * 参数site，siteUrl 可以不传
     *
     * qq分享好友，图片分享，imageUrl必传
     *
     * qq好友or空间分享音乐，必传参数title，titleUrl，text，imageUrl，musicUrl
     */
    private void QzoneShareDetails() {
        platform = ShareSDK.getPlatform(context, QZone.NAME);
        QZone.ShareParams QQparamss = new QZone.ShareParams();
        if (dataSource.getShareType()==SHARE_TEXT)
        {
            //如果设置了site，肯定会设置siteUrl
            if (site!=null)
            {
                QQparamss.setSite(site);
                QQparamss.setSiteUrl(siteUrl);
            }
        }
        //图文分享
        if (dataSource.getShareType()==SHARE_TEXT_IMAGE)
        {
            //如果设置了site，肯定会设置siteUrl
            if (site!=null)
            {
                QQparamss.setSite(site);
                QQparamss.setSiteUrl(siteUrl);
            }

            //图文才需要imageUrl
            if (imageUrl!=null)
            {
                QQparamss.setImageUrl(imageUrl);
            }
        }

        //如果是图片
        /**
         * 如果qq空间如果分享图片的的话就会很奇怪，一般情况最好直接干掉qq空间图片分享
         * qq分享好友，图片分享，imageUrl必传  qq空间不支持图片分享
         */
        if (dataSource.getShareType()==SHARE_IMAGE)
        {
            //图片分享必传参数
            QQparamss.setImageUrl(imageUrl);
            platform.setPlatformActionListener(this);
            platform.share(QQparamss);
            return;
        }
        //如果是music
        /**
         * qq好友or空间分享音乐，必传参数title，titleUrl，text，imageUrl，musicUrl
         */
        if (dataSource.getShareType()==SHARE_MUSIC)
        {
            //音乐分享必传参数
            QQparamss.setImageUrl(imageUrl);
            QQparamss.setMusicUrl(musicUrl);
        }
        QQparamss.setTitle(title);
        QQparamss.setTitleUrl(titleUrl);
        QQparamss.setText(text);
        platform.setPlatformActionListener(this);
        platform.share(QQparamss);

    }

    /**
     * 分享到QQ好友
     */
    private void QQFriendShareDetails() {
        platform = ShareSDK.getPlatform(context, QQ.NAME);
        QQ.ShareParams QQFriendParams = new QQ.ShareParams();
        /**qq好友分享文本就像图文分享那样*/
        if (dataSource.getShareType()==SHARE_TEXT)
        {

            //如果设置了site，肯定会设置siteUrl
            if (site!=null)
            {
                QQFriendParams.setSite(site);
                QQFriendParams.setSiteUrl(siteUrl);
            }
        }
        //图文分享
        if (dataSource.getShareType()==SHARE_TEXT_IMAGE)
        {
            //如果设置了site，肯定会设置siteUrl
            if (site!=null)
            {
                QQFriendParams.setSite(site);
                QQFriendParams.setSiteUrl(siteUrl);
            }
            //图文才需要imageUrl
            if (imageUrl!=null)
            {
                QQFriendParams.setImageUrl(imageUrl);
            }
        }

        //如果是图片
        /**
         * qq分享好友，图片分享，imageUrl必传  qq空间不支持图片分享
         */
        if (dataSource.getShareType()==SHARE_IMAGE)
        {
            //图片分享必传参数
            QQFriendParams.setImageUrl(imageUrl);
            platform.setPlatformActionListener(this);
            platform.share(QQFriendParams);
            return;
        }
        //如果是music
        /**
         * qq好友or空间分享音乐，必传参数title，titleUrl，text，imageUrl，musicUrl
         */
        if (dataSource.getShareType()==SHARE_MUSIC)
        {
            //音乐分享必传参数
            QQFriendParams.setImageUrl(imageUrl);
            QQFriendParams.setMusicUrl(musicUrl);
        }
        QQFriendParams.setTitle(title);
        QQFriendParams.setTitleUrl(titleUrl);
        QQFriendParams.setText(text);
        platform.setPlatformActionListener(this);
        platform.share(QQFriendParams);

    }


    /**
     * 微博分享细节
     */
    private void sinaWeiboShareDetails() {
        OnekeyShare oks = new OnekeyShare();
        oks.setPlatform(SinaWeibo.NAME);
        //显示编辑页
        oks.setSilent(false);
        // 在自动授权时可以禁用SSO方式
        //oks.disableSSOWhenAuthorize();
        //新浪文本分享必须传参数text，type
        if (dataSource.getShareType()==SHARE_TEXT)
        {
            oks.setText(text +atFlag);
        }
        /**
         * 音乐分享，图文分享，图片分享，如果存在编辑视图，那么只需传text，imageUrl，url，音乐需要加musicUrl
         */
        if (dataSource.getShareType()==SHARE_MUSIC||dataSource.getShareType()==SHARE_TEXT_IMAGE||dataSource.getShareType()==SHARE_IMAGE)
        {
            //分享网络图片，新浪分享网络图片，需要申请高级权限,否则会报10014的错误
            //权限申请：新浪开放平台-你的应用中-接口管理-权限申请-微博高级写入接口-statuses/upload_url_text
            //注意：本地图片和网络图片，同时设置时，只分享本地图片
            if (dataSource.getShareType()!=SHARE_IMAGE)
            {
                oks.setText(text +atFlag);
            }

            oks.setImageUrl(imageUrl);
            if (url!=null)
            {
                oks.setUrl(url);
            }
            if (musicUrl!=null)
            {
                oks.setMusicUrl(musicUrl);
            }
        }
        //分享回调，成功或者错误才会回调，取消没有回调
        oks.setCallback(this);
        oks.show(context);
    }

    /**
     * 复制url
     */
    private void copyURlShareDetails() {
        //获取一个剪切板的实例
        if (clipboard==null) {
            clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        }
        if (url!=null)
        {
            ClipData clip = ClipData.newPlainText("copytext", url);
            clipboard.setPrimaryClip(clip);
            Util.alert(context,"复制成功");
        }
        else {
            Util.alert(context,"复制url为空");
            Util.LogUtils("url",url);
        }


    }



    /**
     * SharedStateListen  分享状态接口，用于回调状态，暴露出来让程序员根据状态自行处理
     */
    public interface SharedStateListen{
        void shareState(int state);
    }

    /**
     * 判读微信客户端是否调用
     * @param context
     * @return  返回false，微信客户端没有安装  or  ture，安装了
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
    //设置显示是否显示toast,默认显示
    public  void setShowToast(boolean isShowToast)
    {
        this.isShowToast = isShowToast;
    }

}
