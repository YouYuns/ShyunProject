package com.shyun.shop.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;

import com.shyun.shop.dto.review.ReviewListDto;
import com.shyun.shop.entity.Item;
import com.shyun.shop.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	Page<Review> findAllByItemIdOrderByIdDesc(long itemId, Pageable pageable);

	@Query("select r from Review r inner join r.item i where i.id =:id")
	List<Review> findReviews(@Param("id") Long itemId, Pageable pageable);
	
	@Query("select count(r) from Review r inner join r.item i where i.id =:id")
	Long countReview(@Param("id") Long itemId);

	@Query("select r from Review r inner join r.item i where i.id =:id")
	List<Review> findByItemId(@Param("id") Long itemId);

	@Query("select r from Review r inner join r.item i where i.id=:id order by r.id desc")
	Page<Review> findAllByItemId(@Param("id") Long itemId, Pageable pageable);


	
}
