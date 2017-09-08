package com.example.supportv1.assist.netWork;

/**
 * @author Sun.bl
 * @version [1.0, 2016/4/8]
 */
public abstract class OFThreadApi extends Thread {

    /**
     * 线程区分标识
     */
    public String mTreadName;

    public abstract void cancelWork();

}
