package com.shyun.shop.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.shyun.shop.constant.ItemSellStatus;

import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@Entity
public class OrderItem extends BaseEntity {
	

	@Column(name = "order_item_id")
	@Id @GeneratedValue
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;
	
	private int orderPrice;
	
	private int count;
	
	public static OrderItem createOrderItem(Item item, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setCount(count);
		orderItem.setOrderPrice(item.getPrice());
		item.removeStock(count);
		return orderItem;
	}

	//총가격 메서드
	public int getTotalPrice() { 
		return orderPrice*count;
	}
	
	public void cancel() {
		this.getItem().addStock(count);
	}
	
	
}
