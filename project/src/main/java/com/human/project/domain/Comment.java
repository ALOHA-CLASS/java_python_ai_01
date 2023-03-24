package com.human.project.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Comment {
	
	private int commentNo;
	private int boardNo;
	private int userNo;
	private String writer;
	private String content;
	private int groupNo;
	private int parentNo;
	private int depthNo;
	private int seqNo;
	private LocalDateTime regDate;
	private LocalDateTime updDate;
	private int subCount;			// 자식 댓글 개수
	
	// 자식 댓글 목록
	private List<Comment> subList;

}






