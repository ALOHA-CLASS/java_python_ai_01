package com.human.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.human.project.domain.Comment;
import com.human.project.mapper.CommentMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentMapper replyMapper;

	@Override
	public List<Comment> list() throws Exception {
		List<Comment> replyList = replyMapper.list();
		
		
		for (Comment comment : replyList) {
			log.info(comment.toString());
		}
		
		
		return replyList;
	}

	@Override
	public List<Comment> list(int boardNo) throws Exception {
		List<Comment> replyList = replyMapper.listByBoardNo(boardNo);
		return replyList;
	}

	@Override
	public int insert(Comment comment) throws Exception {
		
		int result = replyMapper.insert(comment);
		
		// 댓글 번호 최댓값
		int replyNo = replyMapper.maxCommentNo();
		log.info("replyNo : " + replyNo);
		comment.setCommentNo(replyNo);
		comment.setGroupNo(replyNo);
		
		result = replyMapper.update(comment);
		
		
		return result;
	}

	@Override
	public int update(Comment comment) throws Exception {
		int result = replyMapper.update(comment);
		return result;
	}

	@Override
	public int delete(int replyNo) throws Exception {
		int result = 0;
		
		// 삭제할 댓글 정보 조회
		Comment comment = replyMapper.select(replyNo);
		
		int subCount = comment.getSubCount();
		
		// 자식 댓글이 없으면, 삭제
		if( subCount == 0 ) {
			// 삭제요청
			result = replyMapper.delete(replyNo);
		}
		// 자식 댓글이 있으면, "삭제된 게시글 입니다" 로 내용 수정
		else {
			comment.setWriter("---");
			comment.setContent("삭제된 게시글 입니다");
			result = replyMapper.update(comment);
			return result;
		}
		
		// 댓글 삭제시, 부모댓글의 자식 개수 - 1 
		int parentNo = comment.getParentNo();
		syncSubCount(parentNo, -1);
		
		return result;
	}

	@Override
	public int insertAnswer(Comment comment) throws Exception {
		
		// 계층번호 + 1
		int depthNo = comment.getDepthNo() + 1;
		// 순서번호  = 부모 댓글의 순서번호 + 부모 댓글의 자식개수 + 1
		int seqNo = comment.getSeqNo() + comment.getSubCount() + 1;
		
		comment.setDepthNo(depthNo);
		comment.setSeqNo(seqNo);
		
		// 뒤의 순서번호 + 1
		replyMapper.syncSeqNo(comment);
		
		// 답글 등록
		int result = replyMapper.insert(comment);
		
		// 부모글의 자식 개수 + 1
		int parentNo = comment.getParentNo();
		syncSubCount(parentNo, 1);
		
		
		return result;
	}
	
	// 5
	// 	10
	// 		12
	// 			14
	// 부모 댓글의 자식 개수 갱신
	public void syncSubCount(int parentNo, int no) throws Exception {
		
		// 부모 정보 조회
		Comment comment = replyMapper.select(parentNo);
		
		if( comment == null ) return;
		
		// 조상 번호
		int ancestorNo = comment.getParentNo();
		
		// 부모 댓글 자식개수 갱신
		replyMapper.syncSubCount(parentNo, no);

		// (종료조건) 원본 댓글에서 멈춤
		if( ancestorNo == 0 ) return;
		
		// 재귀 호출
		syncSubCount(ancestorNo, no);
		
	}

}




















