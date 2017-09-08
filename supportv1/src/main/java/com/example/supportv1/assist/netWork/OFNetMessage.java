package com.example.supportv1.assist.netWork;


import com.example.supportv1.bean.ResultJson;
import com.example.supportv1.bean.RspBaseBean;

/**
 * 响应报文消息体 作为消息传递格式
 *
 * @auth bill
 * @date 2014年7月10日
 **/
public class OFNetMessage {

    public String threadName;
    ;
    public Class<?> beanType;

    /*
     * 联网线程绑定 data，可以为null
     */
    public Object object;

    /*
     * Success 返回 data
     */
    public RspBaseBean responsebean;

    /*
     * Success 直接返回result
     */
    public String results;

    /*
     * 联网没有传入bean
     */
    public ResultJson rjson;

    /*
     * Failure 返回 error string
     */
    public String errors;
}
