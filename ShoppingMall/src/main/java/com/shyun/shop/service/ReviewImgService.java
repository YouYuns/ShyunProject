package com.shyun.shop.service;

import org.springframework.web.multipart.MultipartFile;

import com.shyun.shop.entity.ReviewImg;

public interface ReviewImgService {

	void saveReviewImg(ReviewImg reviewImg, MultipartFile multipartFile) throws Exception;

}
