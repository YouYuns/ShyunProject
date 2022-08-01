package com.shyun.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shyun.shop.constant.CategoryA;
import com.shyun.shop.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	
	long countByCateA(CategoryA cateA);

	List<Category> findByNoBetween(long caNo, long l);

	List<Category> findByCateA(CategoryA catea);


}
