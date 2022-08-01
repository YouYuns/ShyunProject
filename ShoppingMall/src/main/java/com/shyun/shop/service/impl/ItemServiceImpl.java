package com.shyun.shop.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.shyun.shop.constant.CategoryA;
import com.shyun.shop.constant.ItemSellStatus;
import com.shyun.shop.dto.CategoryDto;
import com.shyun.shop.dto.MainItemDto;
import com.shyun.shop.dto.item.ItemDto;
import com.shyun.shop.dto.item.ItemFormDto;
import com.shyun.shop.dto.item.ItemImgDto;
import com.shyun.shop.dto.item.ItemListDto;
import com.shyun.shop.dto.item.ItemSearchDto;
import com.shyun.shop.entity.Item;
import com.shyun.shop.entity.ItemImg;
import com.shyun.shop.entity.Member;
import com.shyun.shop.entity.OrderItem;
import com.shyun.shop.repository.CategoryRepository;
import com.shyun.shop.repository.ItemImgRepository;
import com.shyun.shop.repository.ItemRepository;
import com.shyun.shop.repository.MemberRepository;
import com.shyun.shop.repository.OrderItemRepository;
import com.shyun.shop.service.ItemImgService;
import com.shyun.shop.service.ItemService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final ItemImgService itemImgService;

    private final ItemImgRepository itemImgRepository;

    private final CategoryRepository categoryRepository;
    
    private final MemberRepository memberRepository;
    
    @Override
	public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
    	
        //상품 등록
        Item item = itemFormDto.createItem();
        itemRepository.save(item.addCategory(categoryRepository.findById(itemFormDto.getCaNo()).get()));
        
        
        
        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            if(i == 0)
                itemImg.setRepimgYn("Y");
            else
                itemImg.setRepimgYn("N");

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();
    }
	
	@Override
	@Transactional(readOnly = true)
	public ItemFormDto getItemDtl(Long itemId) {
		
		//이미지조회 -> 등록순으로가져오기 오기위해 오름차순 (엔티티로가져온다)
		List<ItemImg> itemImgList = itemImgRepository.findByItemId(itemId);
		
		//ItemImgDto (Dto)
		List<ItemImgDto> itemImgDtoList = new ArrayList<>();
		
		//엔티티 -> Dto변환
		for(ItemImg itemImg : itemImgList) {
			ItemImgDto itemImgDto = ItemImgDto.of(itemImg); //조회한 ItemImg엔티티를 ItemImgDto객체로 만들어서 리스트에 추가
			itemImgDtoList.add(itemImgDto); 
		}
		//Item조회 (엔티티)
		Item item = itemRepository.findById(itemId)
				.orElseThrow(EntityNotFoundException::new); //상품의 아이디를 통해서 상품엔티티 조회 없으면 예외발생
		if(item.getStockNumber()==0) {
			item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
		}else {
			item.setItemSellStatus(ItemSellStatus.SELL);
		}
		//ModelMapper로 엔티티 -> Dto변환
		ItemFormDto itemFormDto = ItemFormDto.of(item);
		
		//itemFormDto에 itemImgDtoList를 추가해서넣어줌
		itemFormDto.setItemImgDtoList(itemImgDtoList);
		
		return itemFormDto;
	}
	
	
	//상품 업데이트
	@Override
	public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
		//상품 수정 
		Item item = itemRepository.findById(itemFormDto.getId()).orElseThrow(EntityNotFoundException::new);
		item.updateItem(itemFormDto);
		
		//상품이미지 아이디리스트를 조회
		List<Long> itemImgIds = itemFormDto.getItemImgIds();
		
		//이미지 등록
		//상품 이미지르 업데이트-> updateItemImg() 메소드에 상품 이미지 Ids와 이미지파일정보 파라미터로 전달
		for(int i=0; i< itemImgFileList.size(); i++) {
			itemImgService.updateItemImg(itemImgIds.get(i), itemImgFileList.get(i));
		}
		
		return item.getId();
	}
	
	//상품 조건과페이지 받아서 조회
	@Override
	@Transactional(readOnly = true)
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
		return itemRepository.getAdminItemPage(itemSearchDto, pageable);
	}
	
	//메인페이지 조회
	@Override
	@Transactional(readOnly = true)
	public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
		return itemRepository.getMainItemPage(pageable);
	}

	@Override
	public void readCategoryA(Model model) {
		
		
	}

	@Override
	public void itemsListByCaNo(long caNo, Model model) {
		
		model.addAttribute("list",
				itemRepository.findAllByCategoriesNo(caNo).stream()
				.map(ItemListDto::new)
				.collect(Collectors.toList()));
		
	}

	//카테고리별 상품 목록
	@Override
	public void itemsListByCategory(long caNo, Model model) {
		
		model.addAttribute("list",
				itemRepository.findAllByCategoriesNoBetween(caNo, caNo+99).stream()
								.map(ItemListDto::new)
								.collect(Collectors.toList()));//itemsentity에서 categrys를 통해서 접근
		
	}

	@Override
	public List<CategoryDto> categoryList(long caNo) {
		//categoryRepository.findByNoBetween(caNo, caNo+99); //시작값과 끝값 Between 1100-> 1101~1199
		
		
//		//2가지 방법이 있음 위에는 숫자코드로 찾아보는 방법  아래는 문자코드로 비교해서찾아 오는방법 
//		for(CategoryA catea : CategoryA.values()) {
//			if(catea.getCode()==caNo) {
//				categoryRepository.findByCateA(catea);
//			}
//		}
		return categoryRepository.findByNoBetween(caNo, caNo+99)
				.stream().map(CategoryDto::new)
				.collect(Collectors.toList());
	}



}
