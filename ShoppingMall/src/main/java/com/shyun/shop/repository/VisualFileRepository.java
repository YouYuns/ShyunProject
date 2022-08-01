package com.shyun.shop.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shyun.shop.entity.VisualFile;



public interface VisualFileRepository extends JpaRepository<VisualFile, Long>{

	List<VisualFile> findAllByIsShowOrderByNum(boolean isShow);

}
