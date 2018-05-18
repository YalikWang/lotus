package com.lotus.test.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 并发任务模拟抽象类
 * 子类需要实现doTask来实现任务
 * @author yalik_wang
 *
 */
public abstract class AbstractConcurrentTask extends Thread implements Cloneable {
    /**线程计数器*/
    protected static CountDownLatch latch;
    /**任务名称，可以作为身份标志*/
    protected String name;

    @Override
    public void run() {
        //计数减一
        latch.countDown();
        try {
            //是线程处于等待状态，直到计数结束
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.doTask();

    }

    /**
     * 具体的任务处理
     */
    public abstract void doTask();


    public static void initCountDownLatch(int count){
        if(latch==null){
            latch = new CountDownLatch(count);
        }
    }

}
