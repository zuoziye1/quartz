package com.quartz.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: 姚飞虎
 * @Date: 2021/1/11 4:56 下午
 * @Description:
 */
@RestController
public class HelloController {

    @Resource
    private ScheduleHandler scheduleHandler;

    @RequestMapping("/addJob")
    public String addJob(String jName, String jGroup, String tName, String tGroup, String cron) {
        scheduleHandler.addJob(jName, jGroup, tName, tGroup, cron);
        return "success";
    }
}
