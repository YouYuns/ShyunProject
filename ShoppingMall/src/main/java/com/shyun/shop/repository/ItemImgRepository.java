package com.shyun.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyun.shop.entity.ItemImg;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long>{
		//상품이미지 전체 다 리스트로받아오는 쿼리메소드
	  List<ItemImg> findByItemId(Long itemId);

	  //주문상품의 대표이미지를 찾기위한 쿼리메소드
	  ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn);
	  
}
