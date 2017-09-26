package shopex.cn.sharelibrary;

/**
 * Created by p on 2016/6/24.
 */
/**
 *  shareImageUrl     待分享的网络图片
 *  shareImagePath    待分享的本地图片。如果目标平台使用客户端分享，此路径不可以在/data/data下面
 *  shareText         待分享的文本
 *  shareTitleUrl     分享内容标题的链接地址
 *  shareTitle        分享内容的标题
 *  shareSite         QQ空间的字段，标记分享应用的名称
 *  shareSiteUrl      QQ空间的字段，标记分享应用的网页地址
 *  shareType         分享类型，是图文还是文字，音乐等  易信，微信
 *  shareMusicUrl     分享音乐
 *  shareUrl          视频网页地址   消息点击后打开的页面
 */
public   class  ShareViewDataSource
{
    //imageUrl
    private String shareImageUrl;
    private String shareImagePath;
    private String shareText;
    private String shareTitleUrl;
    private String shareTitle;
    private String shareSite;
    private String shareSiteUrl;
    private int shareType;
    private String shareMusicUrl;
    private  String shareUrl;




    /**
     * 文本分享专用构造，目前微信朋友圈不支持文本分享
     * @param shareTitle
     * @param shareTitleUrl
     * @param shareText
     * @param shareType
     */
    public ShareViewDataSource(String shareTitle, String shareTitleUrl, String shareText,int shareType) {
        this.shareTitle = shareTitle;
        this.shareTitleUrl = shareTitleUrl;
        this.shareText = shareText;
        this.shareType = shareType;
    }




    /**
     * 分享图文or网页or图片
     * @param shareTitle
     * @param shareTitleUrl
     * @param shareText
     * @param shareImageUrl
     * @param shareUrl
     * @param shareType
     */
    public ShareViewDataSource(String shareTitle, String shareTitleUrl, String shareText, String shareImageUrl,String shareUrl ,int shareType) {
        this.shareTitle = shareTitle;
        this.shareTitleUrl = shareTitleUrl;
        this.shareText = shareText;
        this.shareImageUrl = shareImageUrl;
        this.shareUrl=shareUrl;
        this.shareType = shareType;
    }




    /**
     * 分享音乐专用
     * @param shareTitle
     * @param shareTitleUrl
     * @param shareText
     * @param shareImageUrl
     * @param shareType
     * @param shareMusicUrl
     * @param shareUrl
     */
    public ShareViewDataSource(String shareTitle, String shareTitleUrl, String shareText, String shareImageUrl,String shareMusicUrl,String shareUrl,int shareType) {
        this.shareTitle = shareTitle;
        this.shareTitleUrl = shareTitleUrl;
        this.shareText = shareText;
        this.shareImageUrl = shareImageUrl;
        this.shareMusicUrl = shareMusicUrl;
        this.shareUrl=shareUrl;
        this.shareType = shareType;
    }

    public void setShareImageUrl(String shareImageUrl) {
        this.shareImageUrl = shareImageUrl;
    }

    public void setShareImagePath(String shareImagePath) {
        this.shareImagePath = shareImagePath;
    }

    public void setShareText(String shareText) {
        this.shareText = shareText;
    }

    public void setShareTitleUrl(String shareTitleUrl) {
        this.shareTitleUrl = shareTitleUrl;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public void setShareSite(String shareSite) {
        this.shareSite = shareSite;
    }

    public void setShareSiteUrl(String shareSiteUrl) {
        this.shareSiteUrl = shareSiteUrl;
    }

    public String getShareImageUrl() {
        return shareImageUrl;
    }

    public String getShareImagePath() {
        return shareImagePath;
    }

    public String getShareText() {
        return shareText;
    }

    public String getShareTitleUrl() {
        return shareTitleUrl;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public String getShareSite() {
        return shareSite;
    }

    public String getShareSiteUrl() {
        return shareSiteUrl;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public void setShareMusicUrl(String shareMusicUrl) {
        this.shareMusicUrl = shareMusicUrl;
    }

    public int getShareType() {
        return shareType;
    }

    public String getShareMusicUrl() {
        return shareMusicUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    @Override
    public String toString() {
        return "ShareViewDataSource{" +
                "shareImageUrl='" + shareImageUrl + '\'' +
                ", shareImagePath='" + shareImagePath + '\'' +
                ", shareText='" + shareText + '\'' +
                ", shareTitleUrl='" + shareTitleUrl + '\'' +
                ", shareTitle='" + shareTitle + '\'' +
                ", shareSite='" + shareSite + '\'' +
                ", shareSiteUrl='" + shareSiteUrl + '\'' +
                ", shareType=" + shareType +
                ", shareMusicUrl='" + shareMusicUrl + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                '}';
    }
}