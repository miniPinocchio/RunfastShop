package com.example.supportv1.assist.netWork;

/**
 * 网络请求结果处理接口,需要在activity或fragment中实现回调方法实现UI更新
 * 
 * @auth bill
 * @date 2014年7月10日
 **/
public interface NetWorkHelpInterf
{
    public void uiSuccess(OFNetMessage msg);

    public void uiError(OFNetMessage msg);

    public void uiFailure(OFNetMessage msg);

    public void uiFinish(OFNetMessage msg);

    public void InterruptNet(String threadName);
}
