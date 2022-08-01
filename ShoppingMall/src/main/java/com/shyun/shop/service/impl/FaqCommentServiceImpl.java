package com.shyun.shop.service.impl;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.shyun.shop.dto.faq.CommentDto;
import com.shyun.shop.dto.faq.CommentInsertDto;
import com.shyun.shop.dto.faq.CommentListDto;
import com.shyun.shop.repository.FaqCommentReposistory;
import com.shyun.shop.service.FaqCommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class FaqCommentServiceImpl implements FaqCommentService{

	private final FaqCommentReposistory faqCommentReposistory;
	
	@Override
	public void write(CommentDto dto) {
		faqCommentReposistory.save(dto.toEntity());
	}

	@Override
	public boolean commnet(CommentInsertDto dto) {
		faqCommentReposistory.save(dto.toEntity());
		return true;
	}

	@Override
	public String comments(long fno, Model model) {
		model.addAttribute("list", faqCommentReposistory.findFaqComments(fno)
				.stream().map(CommentListDto::new).collect(Collectors.toList()));
		return "faq/commentList";
	}

}
