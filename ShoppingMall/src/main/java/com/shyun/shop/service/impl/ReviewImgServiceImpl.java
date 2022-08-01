package com.shyun.shop.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.shyun.shop.entity.ReviewImg;
import com.shyun.shop.repository.ItemImgRepository;
import com.shyun.shop.repository.ReviewImgRepository;
import com.shyun.shop.service.FileService;
import com.shyun.shop.service.ReviewImgService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class ReviewImgServiceImpl implements ReviewImgService {

	@Value("${reviewImgLocation}")
	private String itemImgLocation;
	
	private final ReviewImgRepository reviewImgRepository;
	
	private final FileService fileService;
	
	@Override
	public void saveReviewImg(ReviewImg reviewImg, MultipartFile reviewImgFile) throws Exception {
		String oriImgName = reviewImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl= "";
		
		//파일업로드
				if(!StringUtils.isEmpty(oriImgName)) {
					imgName = fileService.uploadFile(itemImgLocation, oriImgName, reviewImgFile.getBytes());
					
					//저장한 상품 이미지를 불러올 경로 설정 외부 리소스를 불러오는 경로 "/images/**" 설정 
					// uploadPath 프로퍼티 경로 "C:/shop/" 아래 item 폴더에 이미지를 저장하므로 상품이미지를 불러온느 경로로 "images/item"붙여줌
					imgUrl = "/images/review/" + imgName; 
				}
				reviewImg.updateItemImg(imgName, oriImgName, imgUrl);
				reviewImgRepository.save(reviewImg);
	}
}
