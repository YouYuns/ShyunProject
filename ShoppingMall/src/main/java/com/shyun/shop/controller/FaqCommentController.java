package com.shyun.shop.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shyun.shop.dto.faq.CommentDto;
import com.shyun.shop.dto.faq.CommentInsertDto;
import com.shyun.shop.service.FaqCommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FaqCommentController {
	
	private final FaqCommentService faqCommentService;
	
	@ResponseBody
	@PostMapping("/comments")
	public ResponseEntity<String> write(@RequestBody CommentDto dto,Long bno,Principal principal ){
		String commenter = principal.getName();
		dto.setCommenter(commenter);
		dto.setId(bno);
		
		faqCommentService.write(dto);
		return new ResponseEntity<>("OK",HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping("/faq/{fno}/comment")
	public boolean commentInsert(CommentInsertDto dto) {
		return faqCommentService.commnet(dto);
	}
	
	@GetMapping("/faq/{fno}/comments")
	public String comments(@PathVariable Long fno, Model model) {
		return faqCommentService.comments(fno,model);
	}
}
