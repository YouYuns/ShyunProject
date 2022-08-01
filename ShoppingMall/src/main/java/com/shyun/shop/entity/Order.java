package com.shyun.shop.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.shyun.shop.constant.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Table(name = "orders")
@Entity
public class Order extends BaseEntity{
	@Column(name = "order_id")
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	private LocalDateTime orderDate; //주문일
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus; //주문상태
	
	//주문상품정보담기
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem); //orderitem객체를 order객체의 orderitems에 추가 
		orderItem.setOrder(this); //Order엔티티와 OrderItem엔티티 양방향이므로 OrdetItem객체에도 Order객체를세팅
	}
	
	//주문객체만드는메서드
	public static Order createOrder(Member member, List<OrderItem> orderItemList) {
		Order order = new Order();
		order.setMember(member); 
		for(OrderItem orderItem : orderItemList) {  //상품페이지에서는 1개상품 주문하지만 장바구니에서는 여러개 주문가능
			order.addOrderItem(orderItem);          //여러개 주문상품 담을수있도록 리스트형태로 파라미터받음
		}
		
		order.setOrderStatus(OrderStatus.ORDER); //주문상태
		order.setOrderDate(LocalDateTime.now()); //주문시간
		return order;
	}
	//Item클래스에 주문 취소시 주문수량 상품재고에 더해주기 주문상태 취소로바꾸기
	public void cancelOrder() {
		this.orderStatus = OrderStatus.CANCEL;
		
		for(OrderItem orderItem : orderItems) {
			orderItem.cancel();
		}
	}
}
