package com.shyun.shop.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shyun.shop.dto.review.ReviewDto;
import com.shyun.shop.dto.review.ReviewInsertDto;
import com.shyun.shop.dto.review.ReviewUpdateDto;
import com.shyun.shop.service.ReviewService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class ReviewController {
	
	private final ReviewService reviewService;
	
	@GetMapping("/reviews/{id}/write")
	public String write(@PathVariable Long id,Model model) {
		model.addAttribute("id", id);
		model.addAttribute("reviewInsertDto", new ReviewInsertDto());
		return "review/replyWrite";
	}
	
	
	@PostMapping("/reviews/{id}/write")
	public String write(@Valid ReviewInsertDto reviewInsertDto, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes ,@RequestParam("reviewImgFile") List<MultipartFile> reviewImgFile) throws Exception {
		if(bindingResult.hasErrors()) {
			return "review/replyWrite";
			
		}
		reviewService.write(reviewImgFile ,reviewInsertDto, model);
	
		redirectAttributes.addFlashAttribute("successMessage", "리뷰가 등록되었습니다.");
		return "redirect:/item/{id}";
	}
	
	@GetMapping("/reviews/{itemId}/detail/{reviewId}")
	public String detail(@PathVariable Long reviewId, @PathVariable Long itemId, Model model ) {
		model.addAttribute("ReviewDto", new ReviewDto());
		model.addAttribute("reviewId", reviewId);
		model.addAttribute("itemId", itemId);
		reviewService.detail(model, reviewId);
		return "review/replyDetail";
	}
	
	
	@PostMapping("/reviews/{id}/update/{reviewId}")
	public String update(@PathVariable Long reviewId, Model model
			,RedirectAttributes redirectAttributes, ReviewUpdateDto reviewUpdateDto) {
		reviewService.update(model, reviewId, reviewUpdateDto);
		redirectAttributes.addFlashAttribute("successMessage", "리뷰가 수정되었습니다.");
		return "redirect:/item/{id}";
	}
	@ResponseBody
	@DeleteMapping("/reviews/delete/{reviewId}")
	public void delete(@PathVariable Long reviewId, Model model) {
		reviewService.delete(reviewId);
		
	}
	

}
