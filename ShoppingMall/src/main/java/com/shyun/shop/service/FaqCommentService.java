package com.shyun.shop.service;

import org.springframework.ui.Model;

import com.shyun.shop.dto.faq.CommentDto;
import com.shyun.shop.dto.faq.CommentInsertDto;

public interface FaqCommentService {

	void write(CommentDto dto);

	boolean commnet(CommentInsertDto dto);

	String comments(long fno, Model model);

}
