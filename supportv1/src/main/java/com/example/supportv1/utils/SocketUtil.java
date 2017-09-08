package com.example.supportv1.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketUtil
{
    private static final String TAG = "SocketUtil";

    // socket客户端对象
    private Socket client = null;

    /**
     * 初始化socket客户端连接
     * 
     * @param serverIp
     *            服务端IP
     * @param serverPort
     *            服务端端口
     */
    public void initClientSocket(String serverIp, int serverPort)
    {
        try
        {
            this.client = new Socket(serverIp, serverPort);
        }
        catch (UnknownHostException e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 关闭socket连接
     */
    public void closeSocket()
    {
        if (client != null)
        {
            try
            {
                client.close();
            }
            catch (IOException e)
            {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息
     * 
     * @param msg
     *            要发送的消息
     */
    public String sendMessage(String msg)
    {
        PrintWriter out = null;
        InputStream is = null;
        if (client != null)
        {
            try
            {
                // 向服务端发消息
                out = new PrintWriter(client.getOutputStream());
                out.println(msg);
                out.flush();

                // 得到服务端返回的消息
                is = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuffer sb = new StringBuffer();
                String line = "";
                if ((line = br.readLine()) != null)
                {
                    sb.append(line);
                }
                return sb.toString();
            }
            catch (IOException e)
            {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (out != null)
                    {
                        out.close();
                    }
                    if (is != null)
                    {
                        is.close();
                    }
                }
                catch (IOException e)
                {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
