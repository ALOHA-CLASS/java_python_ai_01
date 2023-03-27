package com.human.project.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Comment {
	
	private int commentNo;
	private int boardNo;
	private String userId;
	private String userNick;
	private String content;
	private int groupNo;
	private int parentNo;
	private int depthNo;
	private int seqNo;
	private int subCnt;			// 자식 댓글 개수
	private LocalDateTime regDate;
	private LocalDateTime updDate;
	
	// 자식 댓글 목록
	private List<Comment> subList;

}






