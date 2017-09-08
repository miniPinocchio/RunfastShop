package com.example.supportv1.assist.netWork;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * 报文请求返回消息处理类
 * 
 * @auth bill
 * @date 2014年7月10日
 **/
@SuppressLint("HandlerLeak")
public class OFNetHandler
{

    public static final String TAG = "OFNetHandler";

    protected static final int START = 0;
    protected static final int SUCCESS = 1;
    protected static final int FAILURE = 2;
    protected static final int FINISH = 3;

    private Handler mHandler;

    public OFNetHandler()
    {
        if (Looper.myLooper() != null)
        {
            mHandler = new Handler()
            {
                public void handleMessage(Message msg)
                {
                    OFNetHandler.this.handleMessage(msg);
                }
            };
        }
    }

    /**
     * 对接收的消息进行处理
     * 
     * @param message
     */
    protected void handleMessage(Message message)
    {
        OFNetMessage result = (OFNetMessage) message.obj;
        switch (message.what)
        {
            case START:
                onStart(result);
                break;
            case SUCCESS:
                onSuccess(result);
                break;
            case FAILURE:
                onFailure(result);
                break;
            case FINISH:
                onFinish(result);
                break;

            default:
                break;
        }
    }

    /**
     * 得到消息
     * 
     * @param status
     *            消息状态
     * @param ofmsg
     *            消息
     * @return
     */
    protected Message obtainMessage(int status, OFNetMessage ofmsg)
    {
        Message msg = null;
        if (mHandler != null)
        {
            msg = this.mHandler.obtainMessage(status, ofmsg);
        }
        else
        {
            msg = new Message();
            msg.what = status;
            msg.obj = ofmsg;
        }
        return msg;
    }

    protected void sendMessage(Message msg)
    {
        if (mHandler != null)
        {
            mHandler.sendMessage(msg);
        }
        else
        {
            handleMessage(msg);
        }
    }

    protected void sendStartMessage(OFNetMessage msg)
    {
        sendMessage(obtainMessage(START, msg));
    }

    protected void sendSuccessMessage(OFNetMessage msg)
    {
        sendMessage(obtainMessage(SUCCESS, msg));
    }

    protected void sendFailureMessage(OFNetMessage msg)
    {
        sendMessage(obtainMessage(FAILURE, msg));
    }

    protected void sendFinishMessage(OFNetMessage msg)
    {
        sendMessage(obtainMessage(FINISH, msg));
    }

    /** 联网开始 */
    public void onStart(OFNetMessage msg)
    {
    };

    /** 联网成功，数据返回 */
    public void onSuccess(OFNetMessage msg)
    {
    };

    /** 联网失败 */
    public void onFailure(OFNetMessage msg)
    {
    };

    /** 联网结束 */
    public void onFinish(OFNetMessage msg)
    {
    };

}
