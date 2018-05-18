package com.lotus.test.concurrent;

/**
 * 并发测试帮助类
 * @author yalik_wang
 *
 */
public class ConcurrentTaskHelper {

    /**
     * 模拟并发
     * 主要是针对匿名内部类的模拟实现
     * 因为在测试中我们并不一定需要去明确地定义一个并发任务类
     * @param task 任务目标类
     */
    public static <T extends AbstractConcurrentTask> void simulate(T task,int count){
        simulate(task.getClass(),count);
    }
    /**
     * 模拟并发
     * @param taskClazz 任务目标类
     */
    public static <T extends AbstractConcurrentTask> void simulate(Class<T> taskClazz,int count){
        AbstractConcurrentTask[] tasks = new  AbstractConcurrentTask[count];
        for(int i=0; i<count; i++){
            tasks[i] = getInstance(taskClazz);
        }
        simulate(tasks);
    }

    /**
     * 模拟并发
     * @param tasks
     */
    public static <T extends AbstractConcurrentTask> void simulate(T[] tasks){
        AbstractConcurrentTask.initCountDownLatch(tasks.length);
        for(int i=0; i<tasks.length; i++){
            new Thread(tasks[i]).start();
        }
    }

    private static <T extends AbstractConcurrentTask> T getInstance(Class<T> task){
        T taskObj = null;
        try {
            taskObj = task.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return taskObj;
    }
}
