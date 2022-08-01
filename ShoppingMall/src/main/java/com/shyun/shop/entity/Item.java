package com.shyun.shop.entity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


import com.shyun.shop.constant.ItemSellStatus;
import com.shyun.shop.dto.item.ItemFormDto;
import com.shyun.shop.exception.OutOfStockException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item")
@Getter @Setter
@Entity
public class Item extends BaseEntity{
	@Column(name = "item_id")
	@Id @GeneratedValue
	private Long id; //상품코드
	
	@Column(nullable = false, length = 100)
	private String itemNm; // 상품이름
	
	@Lob
	@Column(nullable = false)
	private String itemDetail; //상품 상세설명
	
	@Column(nullable = false)
	private int discount; //상품 할인율
	
	@Column(name="price" , nullable = false)
	private int price; //상품가격
	
	
	@Column(nullable = false)
	private int stockNumber; //재고수량
	@Column(nullable = false)
	private String brand; //상품제조사
	
	
	@Enumerated(EnumType.STRING)
	private ItemSellStatus itemSellStatus; //상품 판매상태
	
	@Builder.Default
	@OneToMany(mappedBy = "item",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@Builder.Default
	@OneToMany(mappedBy = "item",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Review> reviews = new ArrayList<>();
	
	@Builder.Default
	@JoinColumn //categorys_ca_no;
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<Category> categories = new HashSet<>();
	
	public Item addCategory(Category category) {
		categories.add(category);
		return this;
	}
	
	public Item addReview(Review review) {
		reviews.add(review);
		return this;
	}
	
	public void updateItem(ItemFormDto itemFormDto) {
		this.itemNm = itemFormDto.getItemNm();
		this.itemDetail = itemFormDto.getItemDetail();
		this.discount = itemFormDto.getDiscount();
		this.price = itemFormDto.getPrice();
		this.stockNumber = itemFormDto.getStockNumber();
		this.brand = itemFormDto.getBrand();
		this.itemSellStatus = itemFormDto.getItemSellStatus();
	}
	
	//비즈니스 로직
	//재고뺴기
	public void removeStock(int stockNumber) {
		int restStock = this.stockNumber - stockNumber;
		if(restStock < 0) {
			throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: " + this.stockNumber + ")");
		}
		this.stockNumber = restStock;
	}
	//재고더하기
	public void addStock(int stockNumber) {
		this.stockNumber += stockNumber;
	}
	
	@Builder.Default
	@OneToMany(mappedBy = "item")
	List<ItemImg> imgs=new ArrayList<>();
	

}
