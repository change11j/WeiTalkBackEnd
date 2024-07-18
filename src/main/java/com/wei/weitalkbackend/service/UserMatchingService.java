package com.wei.weitalkbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class UserMatchingService {
    private Queue<WebSocketSession> waitingUsers=new ConcurrentLinkedDeque<>();
    public WebSocketSession findMatch(WebSocketSession session){
        if(waitingUsers.isEmpty()){
            waitingUsers.offer(session);
            return null;
        }else {
            return waitingUsers.poll();
        }
    }
    public void removeFromWaiting(WebSocketSession session){
        waitingUsers.remove(session);
    }
}
