package com.wei.weitalkbackend.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Message {
    private int id;
    private String content;
    private String senderId;
    private String receiverId;
    private LocalDateTime timestamp;
    private boolean isRead;

    public Message() {
    }
}
