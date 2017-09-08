package com.example.supportv1.assist.netWork;

/**
 * @author Sun.bl
 * @version [1.0, 2016/4/8]
 */
public abstract class OFRunnable implements Runnable {


    @Override
    public void run() {
        execute();
    }

    public abstract void execute();

}
