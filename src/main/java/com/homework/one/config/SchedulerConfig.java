package com.homework.one.config;

import com.homework.one.bean.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class SchedulerConfig {

    @Autowired
    SimpMessagingTemplate template;

    //定时推送
    @Scheduled(fixedDelay = 3000)
    public void sendAdhocMessages() {
        //template.convertAndSend("/topic/user", new Message("Fixed Delay Scheduler"));
        //生成url: /user/1/message
        //template.convertAndSendToUser("admin", "/message", new Message("Hi, admin"));
    }
}
