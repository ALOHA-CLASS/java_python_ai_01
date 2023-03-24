package com.human.project.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Board {
	
	private int boardNo;
	private String title;
	private int userNo;
	private String content;
	private int joinCnt;
	private LocalDateTime regDate;
	private LocalDateTime updDate;
	

}
