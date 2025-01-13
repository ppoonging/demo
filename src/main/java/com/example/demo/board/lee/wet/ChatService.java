package com.example.demo.board.lee.wet;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class ChatService {
	
	
	    private final ChatMessageRepository chatMessageRepository;

	    public ChatService(ChatMessageRepository chatMessageRepository) {
	        this.chatMessageRepository = chatMessageRepository;
	    }

	    public List<ChatMessage> getMessagesByBoardId(Long boardId) {
	        return chatMessageRepository.findByBoardId(boardId);
	    }

	    public ChatMessage saveMessage(ChatMessage chatMessage) {
	        return chatMessageRepository.save(chatMessage);
	    }

}
