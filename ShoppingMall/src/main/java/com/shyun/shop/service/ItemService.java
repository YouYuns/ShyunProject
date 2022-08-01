package com.shyun.shop.service;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.shyun.shop.dto.CategoryDto;
import com.shyun.shop.dto.MainItemDto;
import com.shyun.shop.dto.item.ItemFormDto;
import com.shyun.shop.dto.item.ItemSearchDto;
import com.shyun.shop.entity.Item;

public interface ItemService {

	Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception;

	ItemFormDto getItemDtl(Long itemId);

	//상품 업데이트
	Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception;

	//상품 조건과페이지 받아서 조회
	Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

	//메인페이지 조회
	Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

	void readCategoryA(Model model);


	void itemsListByCaNo(long caNo, Model model);


	void itemsListByCategory(long caNo, Model model);

	List<CategoryDto> categoryList(long caNo);


}