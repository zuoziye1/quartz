package com.quartz.不依赖于spring的demo;

/**
 *
 * Job 与 JobDataMap : https://www.w3cschool.cn/quartz_doc/quartz_doc-h4ux2cq6.html
 *
 * @Author: 姚飞虎
 * @Date: 2021/1/11 11:19 上午
 * @Description:
 */

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {

    public static void main(String[] args) throws SchedulerException {

        //创建一个scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getContext().put("skey", "svalue");

        //创建一个Trigger
        /**
         * SimpleTrigger 主要用于一次性执行的 Job（只在某个特定的时间点执行一次），或者 Job 在特定的时间点执行，重复执行 N 次，
         * 每次执行间隔T个时间单位。CronTrigger 在基于日历的调度上非常有用，如“每个星期五的正午”，或者“每月的第十天的上午 10:15”等
         */
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .usingJobData("t1", "tv1")
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();
        trigger.getJobDataMap().put("t2", "tv2");

        //创建一个jobDetail
        /**
         * Jobs很容易实现，在接口中只有一个“execute”方法。本节主要关注：Job的特点、Job接口的execute方法以及JobDetail。
         *
         * 你定义了一个实现Job接口的类，这个类仅仅表明该job需要完成什么类型的任务，除此之外，Quartz还需要知道该Job实例所包含的属性；
         * 这将由JobDetail类来完成。
         *
         * HelloJob : 执行完毕，对该实例的引用就被丢弃了，实例会被垃圾回收
         * 种执行策略带来的一个后果是，job必须有一个无参的构造函数（当使用默认的JobFactory时）；另一个后果是，在job类中，不应该定义有状态的数据属性，因为在job的多次执行中，这些属性的值不会保留。
         * 那么如何给job实例增加属性或配置呢？如何在job的多次执行中，跟踪job的状态呢？答案就是:JobDataMap，JobDetail对象的一部分。
         *
         * 将job加入到scheduler之前，在构建JobDetail时，可以将数据放入JobDataMap
         */
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .usingJobData("jobMap-key-by-usingJobData-1", "indirect-value1")
                .withIdentity("job-1", "group-1").build();
        job.getJobDataMap().put("direct-put-1", "direct-value-1");













        Trigger triggerCopy = TriggerBuilder.newTrigger()
                .withIdentity("triggerCopy", "group1")
                /**
                 * Add the given key-value pair to the Trigger's {@link JobDataMap}.
                 *
                 * @return the updated TriggerBuilder
                 * @see Trigger#getJobDataMap()
                 */
                .usingJobData("t11", "tv11")
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();
        triggerCopy.getJobDataMap().put("t21", "tv21");

        JobDetail jobCopy = JobBuilder.newJob(HelloJob.class)
                .usingJobData("jobMap-key-by-usingJobData-2", "indirect-value2")
                .withIdentity("job-2", "group-1").build();
        job.getJobDataMap().put("direct-put-2", "direct-value-2");

        //注册trigger并启动scheduler
        /**
         * job的一个 trigger 被触发后（稍后会讲到），execute() 方法会被 scheduler 的一个工作线程调用
         */
        scheduler.scheduleJob(job, trigger);
        scheduler.scheduleJob(jobCopy, triggerCopy);
        scheduler.start();

    }
}
