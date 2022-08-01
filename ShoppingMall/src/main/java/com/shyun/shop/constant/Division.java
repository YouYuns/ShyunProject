package com.shyun.shop.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Division {
	
	NOTICE("공지사항","notice"),
	QUS("자주하는질문","qus"),
	QNA("1:1질문하기","qna");
	
	final String title;
	final String url;

}
