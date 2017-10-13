package shopex.cn.sharelibrary;

/**
 * Created by p on 2016/7/1.
 */

import android.content.Context;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 这个类用注册各个分享平台的 AppId AppSecret Enable BypassApproval，也就是用来代替ShareSDK.xml,
 * 在以后中只需要初始化这个类，就不用去对ShareSDK.xml，设置值
 */
//注册平台信息
public class SharedInitRegistInfo {
    /**
     * 微信朋友圈注册数据
     */
    private HashMap<String, Object> map_wxCircle;

    /**
     * 微信好友注册数据
     */
    private HashMap<String, Object> map_wxFriend;

    /**
     * QQ空间注册数据
     */
    private HashMap<String, Object> map_qZone;

    /**
     * QQ好友注册数据
     */
    private HashMap<String, Object> map_qqFriend;

    /**
     * 新浪微博注册数据
     */
    private HashMap<String, Object> map_Sina;


    /**
     * 微信朋友圈分享对象
     */
    private Platform platform_wxCircle;

    /**
     * 微信好友分享对象
     */
    private Platform platform_wxFriend;

    /**
     * QQ空间分享对象
     */
    private Platform platform_Qzone;

    /**
     * QQ好友分享对象
     */
    private Platform platform_qqFriend;

    /**
     * 新浪分享对象
     */
    private Platform platform_Sina;

    public SharedInitRegistInfo(Context context) {
        ShareSDK.initSDK(context, "1b8babbf5af5d");
        /** 初始化各个平台注册信息 */
        initRegistInfo();
    }

    /**
     * 初始化各个平台注册信息
     */
    private void initRegistInfo() {
        // new 实例配置信息集合
        map_wxCircle = new HashMap<String, Object>();
        map_wxFriend = new HashMap<String, Object>();
        map_qZone = new HashMap<String, Object>();
        map_qqFriend = new HashMap<String, Object>();
        map_Sina = new HashMap<String, Object>();

        map_wxCircle.put("AppId", "wx7b0ceb559269b805");
        map_wxCircle.put("AppSecret", "6fbed65b919d3bd1e00f05f8e8fca046");
        map_wxCircle.put("Enable", "true");
        map_wxCircle.put("BypassApproval", "false");
        map_wxCircle.put("ShortLinkConversationEnable", "true");
        ShareSDK.setPlatformDevInfo(WechatMoments.NAME, map_wxCircle);

        map_wxFriend.put("AppId", "wx7b0ceb559269b805");
        map_wxFriend.put("AppSecret", "6fbed65b919d3bd1e00f05f8e8fca046");
        map_wxFriend.put("Enable", "true");
        map_wxFriend.put("BypassApproval", "false");
        map_wxFriend.put("ShortLinkConversationEnable", "true");
        ShareSDK.setPlatformDevInfo(Wechat.NAME, map_wxFriend);

        map_qZone.put("AppId", "101410393");
        map_qZone.put("AppKey", "1e9d93edac5950cf8674c511c96d53b1");
        map_qZone.put("ShareByAppClient", "true");
        map_qZone.put("Enable", "true");
        map_qZone.put("ShortLinkConversationEnable", "true");
        ShareSDK.setPlatformDevInfo(QZone.NAME, map_qZone);

        map_qqFriend.put("AppId", "101410393");
        map_qqFriend.put("AppKey", "1e9d93edac5950cf8674c511c96d53b1");
        map_qqFriend.put("Enable", "true");
        map_qqFriend.put("ShareByAppClient", "true");
        map_qqFriend.put("ShortLinkConversationEnable", "true");
        ShareSDK.setPlatformDevInfo(QQ.NAME, map_qqFriend);


        map_Sina.put("AppKey", "3525482891");
        map_Sina.put("AppSecret", "4791306989ca49f04f7d964d3f775c04");
        map_Sina.put("RedirectUrl", "http://www.sharesdk.cn");
        //map_Sina.put("ShareByAppClient","true");//true
        map_Sina.put("Enable", "true");//true
        map_Sina.put("ShortLinkConversationEnable", "true");
        ShareSDK.setPlatformDevInfo(SinaWeibo.NAME, map_Sina);


    }
}
