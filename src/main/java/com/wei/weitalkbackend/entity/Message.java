package com.wei.weitalkbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class Message {
    @Id
    private int id;
    private String content;
    private String senderId;
    private String receiverId;
    private LocalDateTime timestamp;
    private boolean isRead;

    public Message() {
    }
}
