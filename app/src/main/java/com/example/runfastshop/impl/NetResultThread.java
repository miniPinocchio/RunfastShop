package com.example.runfastshop.impl;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by 天上白玉京 on 2017/7/20.
 */

public class NetResultThread implements Runnable{

    private Handler handler;

    private ResponseBody responseBody;

    public NetResultThread(Handler handler, ResponseBody responseBody) {
        this.handler = handler;
        this.responseBody = responseBody;
    }

    @Override
    public void run() {
        if (responseBody == null || handler == null){
            return;
        }
        try {
            String string = responseBody.string();

            Message message = handler.obtainMessage();
            message.what = 1001;
            message.obj = string;
            handler.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
