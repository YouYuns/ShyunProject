package com.shyun.shop.service.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.shyun.shop.constant.Division;
import com.shyun.shop.dto.faq.FaqDetailDto;
import com.shyun.shop.dto.faq.FaqInsertDto;
import com.shyun.shop.dto.faq.FaqListDto;
import com.shyun.shop.dto.faq.FaqUpdateDto;
import com.shyun.shop.entity.Faq;
import com.shyun.shop.repository.FaqRepository;
import com.shyun.shop.service.FaqService;

@Service
public class FaqServiceImpl implements FaqService{

	@Autowired
	private FaqRepository faqRepository;
	
	@Override
	public void list(Model model, String div) {
		Division division = null;
		Division[] divs=Division.values();
		
		for(Division di :divs) {
			if(div.equals(di.getUrl())) division=di; 
		}
		
		model.addAttribute("list",   faqRepository.findAllByDivision(division).stream()
				.map(FaqListDto::new)
				.collect(Collectors.toList()) );
		
	}

	@Override
	public void list(Model model, int divno) {
		Division division = null;
		Division[] divs=Division.values();
		
		for(Division di :divs) {
			if(divno==di.ordinal())division=di; 
		}
		model.addAttribute("divno", divno);
		model.addAttribute("list",   faqRepository.findAllByDivisionOrderByRegTimeDesc(division).stream()
				.map(FaqListDto::new)
				.collect(Collectors.toList()) );
		
	}

//	@Override
//	public ModelAndView list(ModelAndView mv, int divno) {
//		Division division = null;
//		Division[] divs=Division.values();
//		
//		for(Division di :divs) {
//			if(divno==di.ordinal())division=di; 
//		}
//		
//		mv.addObject("list",   faqRepository.findAllByDivision(division).stream()
//				.map(FaqListDto::new)
//				.collect(Collectors.toList()) );
//		mv.setViewName("/cus/faq/list");
//		return mv;
//	}

	
	@Override
	public void save(FaqInsertDto dto, Model model, int divno) {
		if(divno==0) {
			dto.setDivision(Division.NOTICE);
		}else if(divno==1) {
			dto.setDivision(Division.QUS);
		}else {
			dto.setDivision(Division.QNA);
		}
		faqRepository.save(dto.toEntity());
		model.addAttribute("successMessage", "글이 등록되었습니다.");
	}
	
	@Override
	public void detail(Long fno, Model model) {
		model.addAttribute("list", faqRepository.findById(fno).map(FaqDetailDto::new).get());
	}


	@Override
	public void update(Long fno, Model model, FaqUpdateDto dto) {
		model.addAttribute("successMessage", "수정이 완료되었습니다.");
		faqRepository.findById(fno).map(e-> e.update(dto));
	}

	@Override
	public void delete(Long faqNo, Model model) {
		faqRepository.deleteById(faqNo);
	}

}
