package com.shyun.shop.service.impl;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.shyun.shop.entity.ItemImg;
import com.shyun.shop.repository.ItemImgRepository;
import com.shyun.shop.repository.ItemRepository;
import com.shyun.shop.service.FileService;
import com.shyun.shop.service.ItemImgService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgServiceImpl implements ItemImgService {
	@Value("${itemImgLocation}")
	private String itemImgLocation;
	
	private final ItemImgRepository itemImgRepository;
	
	private final FileService fileService;
	
	@Override
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
		String oriImgName = itemImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl= "";
		
		
		//파일업로드
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			
			//저장한 상품 이미지를 불러올 경로 설정 외부 리소스를 불러오는 경로 "/images/**" 설정 
			// uploadPath 프로퍼티 경로 "C:/shop/" 아래 item 폴더에 이미지를 저장하므로 상품이미지를 불러온느 경로로 "images/item"붙여줌
			imgUrl = "/images/item/" + imgName; 
		}
		
		//상품이미지 정보저장
		
		itemImg.updateItemImg(imgName, oriImgName, imgUrl);
		itemImgRepository.save(itemImg);
		
	}
	
	 //상품이미지 수정
    @Override
	public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
    	//사품이미지 아이디를 이용하여 기존에 저장했던 상품 이미지 엔티티를 조회
    	if(!itemImgFile.isEmpty()) { 
    		ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
    				.orElseThrow(EntityNotFoundException::new);
    		
    		//등록된 상품 이미지파일이 있을경우
    		//기존 이미지 파일 삭제
    		if(!StringUtils.isEmpty(savedItemImg.getImgName())) {
    			fileService.deleteFile(itemImgLocation+"/"+savedItemImg.getImgName());
    		}
    		
    		String oriImgName = itemImgFile.getOriginalFilename();
    		String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
    		String imgUrl = "/images/item/" + imgName;
    		savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
    	}
    }
	
		
}
