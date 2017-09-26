package shopex.cn.sharelibrary;

import java.util.HashMap;

import cn.sharesdk.framework.PlatformDb;

/**
 * Created by niraymond on 16/11/27.
 */

public class WeChatUserInfo extends AuthLoginUserInfo{
    // TODO: 16/11/27 Raymond 加入 微信平台特定的信息

    public WeChatUserInfo(PlatformDb platformDb, HashMap<String, Object> rawData) {
        super(platformDb, rawData);
    }


    @Override
    public String getPlatformName() {
        return "wechat";
    }

    @Override
    public AuthLoginHelper.AuthLoginPlatforms getPlatform() {
        return AuthLoginHelper.AuthLoginPlatforms.Sina_WeiBo;
    }
}
