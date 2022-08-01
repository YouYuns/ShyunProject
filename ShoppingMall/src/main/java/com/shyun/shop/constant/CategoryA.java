package com.shyun.shop.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum CategoryA {
	DISCOUNT("할인상품",1100,"discount"),
	KOREASUL("전통술",1200,"koreasul"),
	ALLSUL("종합술",1300,"allsul");

	public final String koName;
	public final long code;
	public final String lower;
}
