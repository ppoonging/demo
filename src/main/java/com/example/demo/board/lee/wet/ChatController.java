package com.example.demo.board.lee.wet;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
	
	private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat/send") // "/app/chat/send"로 오는 메시지를 처리
    @SendTo("/topic/messages") // "/topic/messages"로 메시지를 전송
    public ChatMessage sendMessage(ChatMessage message) {
        // 메시지 저장
        return chatService.saveMessage(message);
    }
	
	

}
