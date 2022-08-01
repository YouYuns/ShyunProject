package com.shyun.shop.service;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.shyun.shop.dto.visual.VisualInsertDTO;
import com.shyun.shop.dto.visual.VisualUpdateDto;

public interface VisualService {

	String save(MultipartFile vimg, VisualInsertDTO dto);

	String list(Model model);

	String indexList(Model model);

	boolean updateData(long vno, VisualUpdateDto dto);

	boolean updataIsShow(long vno, boolean isShow);

	boolean deleteData(long vno);

}