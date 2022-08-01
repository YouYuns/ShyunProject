package com.shyun.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.shyun.shop.dto.visual.VisualInsertDTO;
import com.shyun.shop.dto.visual.VisualUpdateDto;
import com.shyun.shop.service.VisualService;

import lombok.extern.log4j.Log4j2;
	

@Log4j2	
@Controller
public class VisualController {
	@Autowired
		private VisualService visualService;
	//등록페이지 이동
	@GetMapping("/admin/visuals/write")
	public String page() {
		return "/admin/visual/write";
	}
		
	//등록된 이미지 목록
	@GetMapping("/admin/visuals")
	public String list(Model model) {
		return visualService.list(model);
	}
	
	//index페에지 ajax에서 요청한 메서드 //ajax를 썼다고 꼭 responsebody안써도됨  뷰리졸브가 받아줌
	//등록된 이미지 목록
	@GetMapping("/common/visuals")
	public String indexList(Model model) {
		
		return visualService.indexList(model);
	}
	@ResponseBody
	@PutMapping("/common/visuals/{vno}")
	public boolean updateDate(@PathVariable long vno,VisualUpdateDto dto) {
		return visualService.updateData(vno, dto);
	}
	
	@ResponseBody
	@PutMapping("/admin/visuals/{vno}/isShow")
	public boolean updateIsShow(@PathVariable long vno,boolean isShow) {
		return visualService.updataIsShow(vno, isShow);
	}
	@ResponseBody
	@DeleteMapping("/admin/visuals/{vno}/del")
	public boolean delete(@PathVariable long vno) {
		return visualService.deleteData(vno);
	}
	
	
	//등록 처리 
	@PostMapping("/admin/visuals")
	public String save(MultipartFile vimg, VisualInsertDTO dto) {
		return visualService.save(vimg, dto);
	}
	
	

}
