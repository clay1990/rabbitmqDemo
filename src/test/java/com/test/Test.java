package com.test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: yuyao
 * @Date: 2019/7/10 10:30
 * @Description:
 */
public class Test {

    int i = 0;

    public  void increase() throws Exception {

        ReentrantLock lock = new ReentrantLock(false);
        synchronized (this){

            i++;
            new Thread(() -> {
                try{
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new Error();
                }
            });


        }

    }

}