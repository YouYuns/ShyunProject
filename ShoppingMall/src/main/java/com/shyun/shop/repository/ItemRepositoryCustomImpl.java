package com.shyun.shop.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shyun.shop.constant.ItemSellStatus;
import com.shyun.shop.dto.MainItemDto;
import com.shyun.shop.dto.QMainItemDto;
import com.shyun.shop.dto.item.ItemSearchDto;
import com.shyun.shop.entity.Item;
import com.shyun.shop.entity.QItem;
import com.shyun.shop.entity.QItemImg;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
	
	private JPAQueryFactory queryFactory;

	//JPAQueryFactory의 생성자로 EntityManager객체 넣음
	public ItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	//Querydsl 에서 BooleanExpression where절에서 사용할 수있는 값을 지원
	private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
		return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
	}

	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(StringUtils.equals("all", searchDateType) || searchDateType ==null) {
			return null;
		}else if(StringUtils.equals("1d", searchDateType)) {
			dateTime = dateTime.minusDays(1);
		}else if(StringUtils.equals("1w", searchDateType)) {
			dateTime = dateTime.minusWeeks(1);
		}else if(StringUtils.equals("1m", searchDateType)) {
			dateTime = dateTime.minusMonths(1);
		}else if(StringUtils.equals("6m", searchDateType)) {
			dateTime = dateTime.minusMonths(6);
		}
		//dateTime 값 세팅후 regtime이 dateTime이후 날짜검색
		return QItem.item.regTime.after(dateTime);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("itemNm", searchBy)) {
			return QItem.item.itemNm.like("%" + searchQuery + "%");
		}else if(StringUtils.equals("createdBy", searchBy)) {
			return QItem.item.createdBy.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	
	@Override
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		
		QueryResults<Item> results = queryFactory
				.selectFrom(QItem.item) //상품 데이터 조회
				.where(regDtsAfter(itemSearchDto.getSearchDateType()), // ','로 넣을 경우 and로 인식
						searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
						searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
				.orderBy(QItem.item.id.desc())
				.offset(pageable.getOffset()) //데이터를 가지고 올 시작 인덱스
				.limit(pageable.getPageSize()) //한번에 가지고 올 최대 개수
				.fetchResults(); //조회한 리스트 및 전체 개수를 포함하는 QueryResults반환
				
		List<Item> content = results.getResults();
		long total = results.getTotal();
		return new PageImpl<>(content, pageable, total); //Page클래스의 구현객체인 PageImpl 객체로 반환
	}

	private BooleanExpression itemNmLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%" + searchQuery + "%");
	}
	
	 @Override
	    public Page<MainItemDto> getMainItemPage(Pageable pageable) {
	        QItem item = QItem.item;
	        QItemImg itemImg = QItemImg.itemImg;

	        QueryResults<MainItemDto> results = queryFactory
	                .select(
	                        new QMainItemDto(
	                                item.id,
	                                item.itemNm,
	                                item.itemDetail,
	                                itemImg.imgUrl,
	                                item.price)
	                )
	                .from(itemImg)
	                .join(itemImg.item, item)
	                .where(itemImg.repimgYn.eq("Y"))
	                .orderBy(item.id.desc())
	                .offset(pageable.getOffset())
	                .limit(pageable.getPageSize())
	                .fetchResults();

	        List<MainItemDto> content = results.getResults();
	        long total = results.getTotal();
	        return new PageImpl<>(content, pageable, total);
	    }
}
