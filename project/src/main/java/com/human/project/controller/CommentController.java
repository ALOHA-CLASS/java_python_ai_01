package com.human.project.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.human.project.domain.Comment;
import com.human.project.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	// 댓글 목록
	@GetMapping("/list")
	public String list(Model model, Principal principal, int boardNo) throws Exception {

		if (principal != null) {
			String userId = principal.getName();
			model.addAttribute("userId", userId);
	        // log.info("userId : "+userId);
		}
		
		List<Comment> commentList = commentService.list(boardNo);
		model.addAttribute("commentList", commentList);

		return "comment/list";
	}
	
	// 댓글 등록
	@PostMapping("/insert")
	public String insert(Model model, Comment comment, Principal principal) throws Exception {
		
		int result = 0;
		
		if (principal != null) {
			String userId = principal.getName();
			model.addAttribute("userId", userId);
			// log.info("userId : "+userId);
			result = commentService.insert(comment, userId);
		}
		
		if(result > 0) 			log.info("댓글 등록 성공...");
		else		 			log.info("댓글 등록 실패...");
		
		int boardNo = comment.getBoardNo();
		List<Comment> commentList = commentService.list(boardNo);
		model.addAttribute("commentList", commentList);
		
		return "comment/list";
	}
	
	// 댓글 수정
	@PostMapping("/update")
	public String update(Model model, Comment comment, Principal principal) throws Exception {
		
		int result = 0;
		
		if (principal != null) {
			String userId = principal.getName();
			model.addAttribute("userId", userId);
			// log.info("userId : "+userId);
			result = commentService.update(comment);
		}

		if(result > 0) 			log.info("댓글 수정 성공...");
		else		 			log.info("댓글 수정 실패...");
		
		int boardNo = comment.getBoardNo();
		List<Comment> commentList = commentService.list(boardNo);
		model.addAttribute("commentList", commentList);
		

		return "comment/list";
	}
	
	
	// 댓글 삭제
	@PostMapping("/delete")
	public String delete(Model model, Comment comment, Principal principal) throws Exception {
		
		int commentNo = comment.getCommentNo();
		
		int result = 0;
		
		if (principal != null) {
			String userId = principal.getName();
			model.addAttribute("userId", userId);
			// log.info("userId : "+userId);
			result = commentService.delete(commentNo);
		}
		
		if(result > 0) 			log.info("댓글 삭제 성공...");
		else		 			log.info("댓글 삭제 실패...");
		
		int boardNo = comment.getBoardNo();
		List<Comment> commentList = commentService.list(boardNo);
		model.addAttribute("commentList", commentList);
		
		return "comment/list";
	}
	
	// 답글
	@PostMapping("/answer/insert")
	public String insertAnswer(Model model, Comment comment, Principal principal) throws Exception {
		
		String userId = principal.getName();
		log.info("userId : "+userId);
		
		// 답글 등록 요청
	    int result = commentService.insertAnswer(comment,userId);
		
		if(result > 0) 			log.info("답글 등록 성공...");
		else		 			log.info("답글 등록 실패...");
		
		// 댓글 목록 
		int boardNo = comment.getBoardNo();
		List<Comment> commentList = commentService.list(boardNo);
		model.addAttribute("commentList", commentList);

		return "comment/list";
	}
	
	
	
}















