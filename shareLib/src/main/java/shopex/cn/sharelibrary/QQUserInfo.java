package shopex.cn.sharelibrary;

import java.util.HashMap;

import cn.sharesdk.framework.PlatformDb;

public class QQUserInfo extends AuthLoginUserInfo{
    // TODO: 16/11/27 Raymond 加入 QQ平台特定的信息

    public QQUserInfo(PlatformDb platformDb, HashMap<String, Object> rawData) {
        super(platformDb, rawData);
    }

    @Override
    public String getPlatformName() {
        return "qq";
    }

    @Override
    public AuthLoginHelper.AuthLoginPlatforms getPlatform() {
        return AuthLoginHelper.AuthLoginPlatforms.QQ;
    }
}
