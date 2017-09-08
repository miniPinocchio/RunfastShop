package com.example.supportv1.assist.netWork;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sun.bl
 * @version [1.0, 2016/4/8]
 */
public class OFDispatcher {

    /**
     * 最大请求数
     */
    private int maxRequests = 16;

    /**
     * 正在运行的线程列表
     */
    private List<OFThreadApi> runningThreads = new ArrayList<>();

    /**
     * 等待的线程列表
     */
    private List<OFThreadApi> readyThreads = new ArrayList<>();

    /**
     * 对线程进行排队
     *
     * @param thread
     */
    public void enqueue(OFThreadApi thread) {
        if (runningThreads.size() < maxRequests) {
            runningThreads.add(thread);
            thread.start();
        } else {
            readyThreads.add(thread);
        }
    }

    /**
     * 线程结束
     *
     * @param thread 线程
     */
    public void finished(OFThreadApi thread) {
        runningThreads.remove(thread);
        promoteCall();
    }


    /**
     * 推进线程
     */
    private void promoteCall() {
        if (runningThreads.size() >= maxRequests) {
            return;
        }
        if (readyThreads.isEmpty()) {
            return;
        }
        if (readyThreads.size() == 0) {
            return;
        }
        OFThreadApi thread = readyThreads.remove(0);
        runningThreads.add(thread);
        thread.start();
    }

    /**
     * 取消所有的线程
     */
    public void cancelAll() {
        for (OFThreadApi thread : readyThreads) {
            if (thread == null) {
                continue;
            }
            thread.cancelWork();
        }
        for (OFThreadApi thread : runningThreads) {
            if (thread == null) {
                continue;
            }
            thread.cancelWork();
        }
    }

    /**
     * 取消特定标志的线程
     *
     * @param tag 线程名称
     */
    public void cancelTag(String tag) {
        for (OFThreadApi thread : readyThreads) {
            if (!TextUtils.equals(tag, thread.mTreadName)) {
                continue;
            }
            thread.cancelWork();
        }
        for (OFThreadApi thread : runningThreads) {
            if (!TextUtils.equals(tag, thread.mTreadName)) {
                continue;
            }
            thread.cancelWork();
        }
    }


    /**
     * 获取最大连接数
     *
     * @return
     */
    public int getMaxRequests() {
        return maxRequests;
    }

    /**
     * 设置最大连接数
     *
     * @param maxRequests 线程最大同时运行数量
     */
    public void setMaxRequests(int maxRequests) {
        if (maxRequests < 1) {
            throw new IllegalArgumentException("maxRequest < 1:" + maxRequests);
        }
        this.maxRequests = maxRequests;
        promoteCall();
    }


}
