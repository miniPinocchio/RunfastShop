package shopex.cn.sharelibrary;

/**
 * Created by niraymond on 16/11/27.
 */

public interface AuthLoginListener {
    public void loginSuccess(AuthLoginUserInfo userInfo);
    public void loginFailed(AuthLoginHelper.AuthLoginPlatforms platform, Error error);
    public void loginCanceled(AuthLoginHelper.AuthLoginPlatforms platform);
    public void platformNotFound(AuthLoginHelper.AuthLoginPlatforms platform);
}
