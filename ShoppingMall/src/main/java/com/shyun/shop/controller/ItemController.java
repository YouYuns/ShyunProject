package com.shyun.shop.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shyun.shop.constant.CategoryA;
import com.shyun.shop.dto.CategoryDto;
import com.shyun.shop.dto.item.ItemFormDto;
import com.shyun.shop.dto.item.ItemSearchDto;
import com.shyun.shop.dto.review.ReviewInsertDto;
import com.shyun.shop.dto.review.ReviewListDto;
import com.shyun.shop.entity.Item;
import com.shyun.shop.service.ItemService;
import com.shyun.shop.service.MemberService;
import com.shyun.shop.service.ReviewService;

import groovy.cli.Option;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class ItemController {
	
	private final ReviewService reviewService;
	private final ItemService itemService;
	private final MemberService memberService;
	
	@GetMapping("/common/categorys/{caNo}/items")
	public String itemsListByCano(@PathVariable long caNo, Model model) {
		itemService.itemsListByCaNo(caNo, model);
		return "item/list";
		
	}
	@GetMapping("/common/category-a/{caNo}/items")//1차 카테고리
	public String itemsListByCategory(@PathVariable long caNo, Model model) {
		itemService.itemsListByCategory(caNo, model);
		return "item/list";
	}

	//ajax로 요청
	@GetMapping("/admin/category/{caNo}")
	public String category(@PathVariable long caNo,Model model) {
		model.addAttribute("option", itemService.categoryList(caNo));
		return "admin/item/category-data";
	}

	
	//상품등록 입력폼으로 이동매핑
	@GetMapping("/admin/item/new")
	public String itemForm(Model model) {
		model.addAttribute("cateA", CategoryA.values());
		for(CategoryA cate : CategoryA.values()) {
			System.out.println(cate.getKoName());
		}
		
		model.addAttribute("itemFormDto", new ItemFormDto());
		return "admin/item/itemForm";
	}
	
	
	 //상품등록입력 
    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){
        if(bindingResult.hasErrors()){
            return "admin/item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "admin/item/itemForm";
        }

        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "admin/item/itemForm";
        }

        return "redirect:/";
    }
	
	
	//상품조회
	
	@GetMapping("/admin/item/{itemId}")
	public String itemDtl(@PathVariable("itemId") Long itemId, Model model,Principal principal) {
		
		try {
			//찾아서있으면 해당 itemFormDto를 넘겨줌
			ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
			model.addAttribute("itemFormDto", itemFormDto);
		} catch (Exception e) {
			
			//없으면 에러메세지와 새로운빈 ItemFormDto를만들어서 넘겨줌
			model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
			model.addAttribute("itemFormDto", new ItemFormDto());
			return "admin/item/itemForm";
		}
		return "admin/item/itemForm";
	}

	//상품수정
	@PostMapping("/admin/item/{itemid}")
	public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, 
			@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList,Model model) {
		
		if(bindingResult.hasErrors()) {
			return "admin/item/itemForm";
		}
		
		if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() ==null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값입니다.");
			return "admin/item/itemForm";
		}
		
		try {
			itemService.updateItem(itemFormDto, itemImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
			return "admin/item/itemForm";
		}
		return "redirect:/";
		
	}
	
	
	//상품관리페이지
	@GetMapping(value = {"/admin/items", "/admin/items/{page}"})
	public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get(): 0, 5);
		
		Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
		model.addAttribute("items", items);
		model.addAttribute("itemSearchDto", itemSearchDto);
		model.addAttribute("maxPage", 5);
		return "admin/item/itemMng";
	}
	
	//상품상세정보 페이지 (기존 상품 정보가져오는 서비스 그대로이용)
	@GetMapping(value = {"/item/{itemId}", "/item/{itemId}/{page}"})
	public String itemDtl(Model model, @PathVariable("itemId") Long itemId, Principal principal
						, @PathVariable("page") Optional<Integer> page) {
		ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get(): 0, 3);
		Page<ReviewListDto> reviewList = reviewService.getReviewDtl(itemId, model, pageable);
		
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("item", itemFormDto);
		model.addAttribute("maxPage", 5);
		return "item/itemDtl";
	}
	

}
