package com.shyun.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.shyun.shop.dto.review.ReviewDetailDto;
import com.shyun.shop.dto.review.ReviewImgDto;
import com.shyun.shop.dto.review.ReviewInsertDto;
import com.shyun.shop.dto.review.ReviewListDto;
import com.shyun.shop.dto.review.ReviewUpdateDto;
import com.shyun.shop.entity.Review;
import com.shyun.shop.entity.ReviewImg;
import com.shyun.shop.repository.ReviewImgRepository;
import com.shyun.shop.repository.ReviewRepository;
import com.shyun.shop.service.ReviewImgService;
import com.shyun.shop.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
	private final ReviewRepository reviewRepository;
	
	private final ReviewImgRepository reviewImgRepository;
	
	private final ReviewImgService reviewImgService;

	@Transactional(readOnly = true)
	@Override
	public Page<ReviewListDto> getReviewPage(Long itemId, Pageable pageable) {
		
			 List<Review> reviews = reviewRepository.findReviews(itemId, pageable);
			 Long totalCount = reviewRepository.countReview(itemId);
			 List<ReviewListDto> reviewDtos = new ArrayList<>();
			 
			 return new PageImpl<ReviewListDto>(reviewDtos, pageable, totalCount);
		}



	@Override
	public void detail(Model model, Long reviewId) {
		 ReviewDetailDto review = reviewRepository.findById(reviewId).map(ReviewDetailDto::new).get();
		model.addAttribute("reviewDetail",review); 
		
	}
	@Override
	public void update(Model model, Long reviewId, ReviewUpdateDto reviewUpdateDto) {
		reviewRepository.findById(reviewId).map(e->e.update(reviewUpdateDto));
	}

	@Override
	public void delete(Long reviewId) {
		reviewRepository.deleteById(reviewId);
	}

	@Override
	public Long write(List<MultipartFile> reviewImgFile, ReviewInsertDto reviewInsertDto, Model model) throws Exception {
		Review review =reviewInsertDto.toReview();
		reviewRepository.save(review);
		
		//파일업로드
		//String url ="/img/visual/"; //업로드 서버 파일위치 
		for(int i=0; i< reviewImgFile.size();i++) {
			ReviewImg reviewImg = new ReviewImg();
			reviewImg.setReview(review);
			
		//FileData fileData = MyFileUtils.upload(reviewImgFile.get(i),url);
		//reviewInsertDto.addFileData(fileData);
		//db저장
	//	reviewRepository.save(reviewInsertDto.toReview());
			reviewImgService.saveReviewImg(reviewImg, reviewImgFile.get(i));
		}
		return review.getId();
	}
	@Transactional(readOnly = true)
	@Override
	public Page<ReviewListDto> getReviewDtl(Long itemId, Model model,Pageable pageable) {
		List<ReviewListDto> reviewList = reviewRepository.findAllByItemId(itemId, pageable).stream().map(ReviewListDto::new).collect(Collectors.toList()); 
		Long totalCount = reviewRepository.countReview(itemId);

		return new PageImpl<ReviewListDto>(reviewList, pageable, totalCount);
	}
	
	
		
}
