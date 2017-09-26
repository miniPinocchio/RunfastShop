package shopex.cn.sharelibrary;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by p on 2016/11/8.
 */

public class AuthLoginHelper implements PlatformActionListener {
    private Context applicationContext;
    private AuthLoginListener authLoginListener;

    /**
     * 第三方平台登录目前支持qq，微信，新浪登录
     */
    public enum AuthLoginPlatforms{
        QQ,
        WeChat,
        Sina_WeiBo,
        undefined
    }

    private static final int MSG_AUTH_CANCEL = 0x101;
    private static final int MSG_AUTH_ERROR = 0x102;
    private static final int MSG_AUTH_COMPLETE = 0x103;
    private  AuthLoginPlatforms currentPlatform = AuthLoginPlatforms.undefined;


    private PlatformDb platformDb=null;

    /*
    * 不允许默认构造实体对象
    * */
    private AuthLoginHelper(){
        this.applicationContext = applicationContext;
    };

    public AuthLoginHelper(Context applicationContext, AuthLoginListener authLoginListener) {
        if(authLoginListener == null){
            throw new NullPointerException("必需设置回调函数");
        }

        this.applicationContext = applicationContext;
        this.authLoginListener = authLoginListener;

        /** 注册平台信息*/
        new SharedInitRegistInfo(applicationContext);
    }

    public void login(AuthLoginPlatforms platform) {
        switch (platform) {
            case QQ://qq第三方登录,QQ空间
                Platform qzone = ShareSDK.getPlatform(QQ.NAME);
                authorize(qzone);
                currentPlatform=AuthLoginPlatforms.QQ;
                break;
            case WeChat://微信第三方登录
                //微信登录
                //测试时，需要打包签名；sample测试时，用项目里面的demokey.keystore
                //打包签名apk,然后才能产生微信的登录
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                if (!wechat.isClientValid()) {
                    this.authLoginListener.loginFailed(AuthLoginPlatforms.WeChat, new Error("微信初始化失败"));
                    return;
                }
                currentPlatform=AuthLoginPlatforms.WeChat;
                authorize(wechat);
                break;
            case Sina_WeiBo://新浪第三方登录
                //新浪微博
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                authorize(sina);
                currentPlatform=AuthLoginPlatforms.Sina_WeiBo;
                break;
        }
    }



    //执行授权,获取用户信息
    private void authorize(Platform plat) {
        if (plat == null) {
            this.authLoginListener.loginFailed(AuthLoginPlatforms.WeChat, new Error("平台信息错误"));
            return;
        }

        plat.setPlatformActionListener(this);
        //关闭SSO授权
        plat.SSOSetting(false);//此处设置为false，则在优先采用客户端授权的方法，设置true会采用网页方式 );
        plat.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        if (action == Platform.ACTION_USER_INFOR) {
            PlatformDb db = platform.getDb();
            AuthLoginUserInfo userInfo = null;
            switch (currentPlatform){
                case QQ:
                    userInfo = new QQUserInfo(db, hashMap);
                    break;
                case Sina_WeiBo:
                    userInfo = new SinaWeiBoUserInfo(db, hashMap);
                    break;
                case WeChat:
                    userInfo = new WeChatUserInfo(db, hashMap);
                    break;
            }

            final AuthLoginListener listener = this.authLoginListener;
            final AuthLoginUserInfo info = userInfo;
            new Handler(applicationContext.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    listener.loginSuccess(info);
                }
            });
        }

        cleanPlatform(currentPlatform);
    }

    @Override
    public void onError(Platform platform, int action, Throwable throwable) {
        cleanPlatform(currentPlatform);

        if (action == Platform.ACTION_USER_INFOR) {
            final AuthLoginListener listener = this.authLoginListener;
            final String message = throwable.getMessage();
            new Handler(applicationContext.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    listener.loginFailed(currentPlatform, new Error(message));
                }
            });
        }
    }

    @Override
    public void onCancel(Platform platform, int action) {
        cleanPlatform(currentPlatform);

        if (action == Platform.ACTION_USER_INFOR) {
            final AuthLoginListener listener = this.authLoginListener;
            new Handler(applicationContext.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    listener.loginCanceled(currentPlatform);
                }
            });
        }
    }

    /**获取第三方平台的数据信息*/
    public PlatformDb getPlatformDb() {
        return platformDb;
    }

    public void cleanPlatform(AuthLoginPlatforms platform)
    {
        switch (platform)
        {
            case QQ:
                cleanQQ();
                break;
            case WeChat:
                cleanWeiXin();
                break;
            case Sina_WeiBo:
                cleanSina();
                break;
            default:
                break;
        }
    }

    /**
     * 需要说明的是，shardsdk会自动将授权的用户信息保存到本地，所以要除清授权信息的话，可以手动清除缓存。或者调用removeAccout()方法
     */

    public void cleanQQ() {
        Platform qzone = ShareSDK.getPlatform(applicationContext, QQ.NAME);//此处可以设置对应的第三方平台名称来清理对应的信息
        if (qzone.isValid()) {
            qzone.removeAccount();
        }
    }

    public void cleanWeiXin() {
        Platform weixin = ShareSDK.getPlatform(applicationContext, Wechat.NAME);//此处可以设置对应的第三方平台名称来清理对应的信息
        if (weixin.isValid()) {
            weixin.removeAccount();
        }
    }

    public void cleanSina() {
        Platform sina = ShareSDK.getPlatform(applicationContext, SinaWeibo.NAME);//此处可以设置对应的第三方平台名称来清理对应的信息
        if (sina.isValid()) {
            sina.removeAccount();
        }
    }


    /**
     * 通常的做法在oncreate  初始化sharedsdk，在onDestroy停止，也可以不调用这个方法，我只是暴露出来
     */
    public void OnStopSDK() {
        ShareSDK.stopSDK(applicationContext);
    }
}
