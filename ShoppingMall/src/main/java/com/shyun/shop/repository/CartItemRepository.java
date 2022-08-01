package com.shyun.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shyun.shop.dto.CartDetailDto;
import com.shyun.shop.entity.CartItem;

//장바구니에 들어갈 상품을 저장하거나 조회
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	//상품이 장바구니에 들어있는지 조회
	CartItem findByCartIdAndItemId(Long cartId, Long itemId);
	
	//생성자 파라미터 순서로 넣어야됨 , Dto생성자 이용해서 반환할때는 new키워드 + 경로 다써줘야됨
	@Query("select new com.shyun.shop.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl)" 
			+ "from CartItem ci, ItemImg im "
			+ "join ci.item i " 
			+ "where ci.cart.id = :cartId "
			+ "and im.item.id = ci.item.id "
			+ "and im.repimgYn = 'Y' " 
			+ "order by ci.regTime desc"
			)
	List<CartDetailDto> findCartDetailDtoList(@Param("cartId") Long cartId);
}
