package com.wei.weitalkbackend.controller;

import com.wei.weitalkbackend.entity.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
//    @MessageMapping("chat.sendMessage")
//    @SendTo("topic/public")
//    public Message sendMessage(Message message){
//        return message;
//    }
}
