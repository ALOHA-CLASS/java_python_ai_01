package com.human.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.human.project.domain.Reply;

@Mapper
public interface ReplyMapper {

	// 댓글 전체 조회
	public List<Reply> list() throws Exception;
	
	// 댓글 목록 - 게시글 번호
	public List<Reply> listByBoardNo(int boardNo) throws Exception;
	
	// 댓글 등록
	public int insert(Reply reply) throws Exception;
	
	// 댓글 수정
	public int update(Reply reply) throws Exception;
	
	// 댓글 삭제
	public int delete(int replyNo) throws Exception;

	// [최대값] 그룹 번호
	public int maxReplyNo() throws Exception;

	// 답글 순서번호 갱신
	public int syncSeqNo(Reply reply) throws Exception;

	// 부모의 자식 개수 갱신
	public void syncSubCount(@Param("parentNo")int parentNo, @Param("no") int no) throws Exception;

	// 댓글 조회
	public Reply select(int replyNo) throws Exception;

}
