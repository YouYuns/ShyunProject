package com.shyun.shop.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.shyun.shop.dto.faq.FaqInsertDto;
import com.shyun.shop.dto.faq.FaqUpdateDto;

public interface FaqService {

	void list(Model model, String div);

	void list(Model model, int divno);

//	ModelAndView list(ModelAndView mv, int divno);

	void save(FaqInsertDto dto, Model model, int divno);

	void detail(Long fno, Model model);

	void update(Long fno, Model model, FaqUpdateDto dto);

	void delete(Long faqNo, Model model);
}
