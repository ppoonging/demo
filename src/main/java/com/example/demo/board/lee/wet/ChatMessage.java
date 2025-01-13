package com.example.demo.board.lee.wet;


	import jakarta.persistence.*;
	import lombok.Data;

	@Entity
	@Data
	public class ChatMessage {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Long boardId; // 게시글 ID와 연결

	    private String sender;

	    private String content;

	    private String timestamp;
	}

	

