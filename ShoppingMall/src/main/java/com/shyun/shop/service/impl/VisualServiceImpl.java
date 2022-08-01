package com.shyun.shop.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.shyun.shop.dto.FileData;
import com.shyun.shop.dto.visual.VisualInsertDTO;
import com.shyun.shop.dto.visual.VisualListDTO;
import com.shyun.shop.dto.visual.VisualUpdateDto;
import com.shyun.shop.entity.VisualFile;
import com.shyun.shop.repository.VisualFileRepository;
import com.shyun.shop.service.VisualService;
import com.shyun.shop.service.VisualService;
import com.shyun.shop.util.MyFileUtils;

@Service
public class VisualServiceImpl implements VisualService{

	
	@Autowired
	private VisualFileRepository visualFileRepository;
	
	
		
	@Override
	public String save(MultipartFile vimg, VisualInsertDTO dto) {
		
		//파일업로드
		String url ="/img/visual/"; //업로드 서버 파일위치 
		FileData fileData = MyFileUtils.upload(vimg,url);
		dto.addFileData(fileData);
		//db저장
		visualFileRepository.save(dto.toVisualFile());
		
		return "redirect:/admin/visuals";
	}

	@Override
	public String list(Model model) {
		model.addAttribute("list", visualFileRepository.findAll().stream()
				.map(VisualListDTO::new).collect(Collectors.toList()));
		
		return "admin/visual/list"; //관리자 페이지에 뿌리는 이미지
	}

	@Override
	public String indexList(Model model) {
		model.addAttribute("vlist", visualFileRepository.findAllByIsShowOrderByNum(true).stream()
				.map(VisualListDTO::new).collect(Collectors.toList()));
		return "/visual/list"; //인덱스에 뿌리는 이미지
	}


	@Override
	public boolean updateData(long vno, VisualUpdateDto dto) {
		 Optional<VisualFile> result = visualFileRepository.findById(vno);
		 if(result.isEmpty()) return false;
		 
		 visualFileRepository.save(result.get().updateData(dto));
		return true;
	}

	@Override
	public boolean updataIsShow(long vno, boolean isShow) {
		 Optional<VisualFile> result = visualFileRepository.findById(vno);
		 if(result.isEmpty()) return false;
		 visualFileRepository.save(result.get().updateIsShow(isShow));
		return true;
	}

	@Override
	public boolean deleteData(long vno) {
		Optional<VisualFile> result = visualFileRepository.findById(vno);
		if(result.isPresent()) {
		visualFileRepository.deleteById(vno);
		return true;
		}
		return false;
	}
}
