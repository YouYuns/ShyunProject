package com.shyun.shop.util;

import lombok.Getter;

@Getter
public class PageInfo {
	int total;
	int start;
	int end;
		
	public static PageInfo getInstance(int pageNo, int pageTotal) {
		return new PageInfo(pageNo, pageTotal);
	}
	
	
	private PageInfo(int pageNo, int pageTotal) {
		total = pageTotal;
		calcPage(pageNo);
	}


	private void calcPage(int pageNo) {
		int pageBlock = 5;
		int blockNo = pageNo / pageBlock; 
		if(pageNo % pageBlock != 0)
			blockNo++;
		
		end = pageBlock * blockNo;
		start  = end - pageBlock +1;
		if(end> total ) end = total;
		
	}
	
	
}