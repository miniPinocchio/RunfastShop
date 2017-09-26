package shopex.cn.sharelibrary;

import java.util.HashMap;

import cn.sharesdk.framework.PlatformDb;

/**
 * Created by niraymond on 16/11/27.
 */

public class SinaWeiBoUserInfo extends AuthLoginUserInfo {
    // TODO: 16/11/27 Raymond 加入 新浪微博平台特定的信息
    public SinaWeiBoUserInfo(PlatformDb platformDb, HashMap<String, Object> rawData) {
        super(platformDb, rawData);
    }

    @Override
    public String getPlatformName() {
        return "weibo";
    }

    @Override
    public AuthLoginHelper.AuthLoginPlatforms getPlatform() {
        return AuthLoginHelper.AuthLoginPlatforms.Sina_WeiBo;
    }
}
