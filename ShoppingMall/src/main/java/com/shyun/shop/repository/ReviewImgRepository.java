package com.shyun.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import com.shyun.shop.entity.ReviewImg;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long>{

	List<ReviewImg> findByReviewId(Long reviewId);

	

}
