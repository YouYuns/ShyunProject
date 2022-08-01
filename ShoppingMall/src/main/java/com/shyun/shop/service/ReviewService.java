package com.shyun.shop.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.shyun.shop.dto.review.ReviewDto;
import com.shyun.shop.dto.review.ReviewInsertDto;
import com.shyun.shop.dto.review.ReviewListDto;
import com.shyun.shop.dto.review.ReviewUpdateDto;
import com.shyun.shop.entity.Review;
import com.shyun.shop.repository.ReviewRepository;

public interface ReviewService {
	

	Long write(List<MultipartFile> reviewImgFile, ReviewInsertDto reviewInsertDto, Model model) throws Exception;

	Page<ReviewListDto> getReviewPage(Long itemId, Pageable pageable);


	void detail(Model model, Long reviewId);

	void update(Model model, Long reviewId, ReviewUpdateDto reviewUpdateDto);

	void delete(Long reviewId);

	Page<ReviewListDto> getReviewDtl(Long itemId, Model model, Pageable pageable);





	
}
