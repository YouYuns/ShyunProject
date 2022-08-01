package com.shyun.shop.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.shyun.shop.entity.Item;
																									//querydal로 구현한 상품관리 페이지목록
public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> , ItemRepositoryCustom{

	List<Item> findByItemNm(String itemNm);
	
	
	@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
	List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

		List<Item> findAllByCategoriesNoBetween(long caNo, long l);


		List<Item> findAllByCategoriesNo(long caNo);



}
