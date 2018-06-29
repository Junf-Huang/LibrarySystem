package com.homework.one.controller;

import com.homework.one.bean.Message;
import com.homework.one.bean.User;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MessageController {

    @Autowired
    SimpMessagingTemplate template;

    @GetMapping(value = "/chat")
    public String getChat(){
        return "chat";
    }

    // /app/user 客户端发送至服务端
    @MessageMapping("/user")
    //  服务端广播给客户端
    //@SendToUser可以将消息只返回给发送者
    //@SendTo会将消息广播给所有订阅/user这个路径的用户
    @SendTo("/topic/user")  //广播要全路径 @SendToUser默认有前缀/user
    public Message getUser(@NonNull Message message) {

        return new Message("Hi " + message.getContent());
    }

    /*
        使用@DestinationVariable接收url中的参数
     */
    @MessageMapping("/{chatId}/message")
    //@SendToUser("/message") //生成的url:/user/登录名/message
    public void send(Message message,
                     @DestinationVariable(value="chatId") String chatId) {

        log.info("message={}",message.getContent());
        log.info("chatId={}",chatId);

        //return new Message("Hi " +message.getContent());
        //template.convertAndSend("/topic/"+topic, message);    //类似于SendTo

        template.convertAndSendToUser(chatId, "/message", message);
        //useId==admin
        //template.convertAndSendToUser("101", "/message", message);
    }

    @MessageMapping(value = "/{chatId}/greet")
    public void greet(@DestinationVariable(value = "chatId") String chatId){
        log.info("greet={}", chatId);
        template.convertAndSendToUser(chatId,"/greet", new Message("Welcome to the library"));
        //消息转发之间也会互相影响，注意路径的设置
    }
}
