package com.shyun.shop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.shyun.shop.constant.CategoryA;
import com.shyun.shop.entity.Category;
import com.shyun.shop.entity.Item;
import com.shyun.shop.repository.CategoryRepository;
import com.shyun.shop.repository.ItemRepository;

@SpringBootTest
class ShoppingMallApplicationTests {

	@Autowired
	CategoryRepository categoryRepository;
	
	
	@Autowired
	ItemRepository itemRepository;
	
//	@Commit
//	@Transactional
//	//@Test
//	void 移댄뀒怨좊━_�뿰寃�() {
//		
//		Item entity = itemRepository.findById(2L).get();
//		Category category = categoryRepository.findById(1301L).get();
//		itemRepository.save(entity.addCategory(category));
//	}
//
	@Test
	void 카테고리추가() {
	CategoryA categoryA = CategoryA.KOREASUL;
		long count = categoryRepository.countByCateA(categoryA);
		
		  categoryRepository.save( Category.builder() 
				  .name("맥주") 
				  .code(++count)
				  .cateA(categoryA)
		  .build().createNo());
		 
	}
	
}
