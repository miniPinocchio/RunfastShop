package shopex.cn.sharelibrary;

import java.util.HashMap;

import cn.sharesdk.framework.PlatformDb;

/**
 * Created by niraymond on 16/11/27.
 */

public abstract class AuthLoginUserInfo {
    protected String name;
    protected String avatar;
    /*各平台对应的用户ID*/
    protected String authId;
    protected String country;
    protected String gender;

    /*
    * 返回平台信息
    *
    * */
    abstract public String getPlatformName();
    abstract public AuthLoginHelper.AuthLoginPlatforms getPlatform();

    public AuthLoginUserInfo(PlatformDb platformDb, HashMap<String, Object> rawData){
        authId = platformDb.getUserId();
        name = platformDb.getUserName();
        platformDb.getUserId();
        avatar = platformDb.getUserIcon();
        country = "CN";
        gender = "";
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}


