package com.shyun.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shyun.shop.dto.faq.FaqInsertDto;
import com.shyun.shop.dto.faq.FaqUpdateDto;
import com.shyun.shop.service.FaqService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class FaqController {

		private final FaqService faqService;
			
		@GetMapping("/faq")
		public String Faq() {
			return "faq/index";
		}
	
		//ajax로 요청시 리턴 list.html페이지가 response된다. --> result 
		@GetMapping("/faq/{divno}")
		public String list(@PathVariable int divno, Model model) {
			faqService.list(model, divno);
			return "faq/list";
		}
		
		
		//글쓰기페이지
		@GetMapping("/faq/{divno}/write")
		public String write(@PathVariable int divno, Model model){
			model.addAttribute("divno", divno);
			return "faq/write";
		}
		
		//글쓰기페이지 POST
		@PostMapping("/faq/{divno}/write")
		public String save(@PathVariable int divno, FaqInsertDto dto, Model model){
			faqService.save(dto,model, divno);
			return "faq/index";
		}
		
		//디테일페이지 이동
		@GetMapping("/faq/{fno}/detail")
		public String detail(@PathVariable Long fno, Model model) {
			faqService.detail(fno,model);
			return "faq/detail";
		}
		
		//수정하기
				@PostMapping("/faq/{fno}/update")
				public String update(@PathVariable long fno, Model model, FaqUpdateDto dto) {
					faqService.update(fno,model, dto);
					return "redirect:/faq";
				}
				
		@ResponseBody
		@DeleteMapping("/faq/delete/{faqNo}")
				public void delete(@PathVariable Long faqNo, Model model) {
					faqService.delete(faqNo, model);
				}
			
		
		
}
