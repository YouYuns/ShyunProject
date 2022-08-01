package com.shyun.shop.service;

import org.springframework.web.multipart.MultipartFile;

import com.shyun.shop.entity.ItemImg;

public interface ItemImgService {

	void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception;

	//상품이미지 수정
	void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception;

}