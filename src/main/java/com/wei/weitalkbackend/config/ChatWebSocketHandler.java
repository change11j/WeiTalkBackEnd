package com.wei.weitalkbackend.config;

import com.wei.weitalkbackend.service.UserMatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private UserMatchingService matchingUsers;
    private Map<WebSocketSession,WebSocketSession> pairedSessions = new ConcurrentHashMap<>();

    public ChatWebSocketHandler(UserMatchingService matchingUsers) {
        this.matchingUsers = matchingUsers;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        WebSocketSession match = matchingUsers.findMatch(session);
        if (match!=null){
            pairedSessions.put(session,match);
            pairedSessions.put(match,session);
            notifyMatch(session,match);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload=message.getPayload();//持久化用的
        WebSocketSession recipient=pairedSessions.get(session);
        if(recipient!=null&&recipient.isOpen()){
            recipient.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        WebSocketSession paired=pairedSessions.get(session);
        if (paired!=null){
            pairedSessions.remove(paired);
            matchingUsers.removeFromWaiting(paired);
            notifyDisconnection(paired);
        }
        matchingUsers.removeFromWaiting(session);
    }
    private void notifyMatch(WebSocketSession session1,WebSocketSession session2) throws IOException {
        session1.sendMessage(new TextMessage("已連線"));
        session2.sendMessage(new TextMessage("已連線"));

    }
    private void notifyDisconnection(WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage("對方已離開"));
    }
}
