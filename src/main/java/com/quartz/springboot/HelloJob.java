package com.quartz.springboot;

/**
 * @Author: 姚飞虎
 * @Date: 2021/1/11 4:46 下午
 * @Description:
 */

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/**
 * hello job
 */
public class HelloJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        mergedJobDataMap.forEach((key,value)-> System.out.println("key:"+key+"\tvalue:"+value));
        System.out.println(LocalDateTime.now()+"hello world");
    }
}


