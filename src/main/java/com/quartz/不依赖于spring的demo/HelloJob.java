package com.quartz.不依赖于spring的demo;

import org.quartz.*;

import java.time.LocalDateTime;

/**
 * @Author: 姚飞虎
 * @Date: 2021/1/11 11:58 上午
 * @Description:
 */
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap triggerJobDataMap = context.getTrigger().getJobDataMap();
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        triggerJobDataMap.forEach((k,v)-> System.out.println("key:"+k+",value:"+v));
        jobDataMap.forEach((k,v)-> System.out.println("key:"+k+",value:"+v));

        /**
         * 在job的执行过程中，可以从JobDataMap中取出数据
         */
        try {
            SchedulerContext context1 = context.getScheduler().getContext();
            context1.forEach((k,v)-> System.out.println("key:"+k+",value:"+v));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        System.out.println(LocalDateTime.now()+"===========================");
    }

}
