package com.shyun.shop.repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shyun.shop.constant.Division;
import com.shyun.shop.entity.Faq;


public interface FaqRepository extends JpaRepository<Faq, Long>{
	List<Faq> findAllByDivision(Division division);

	Collection<Faq> findAllByDivisionOrderByRegTimeDesc(Division division);




}
