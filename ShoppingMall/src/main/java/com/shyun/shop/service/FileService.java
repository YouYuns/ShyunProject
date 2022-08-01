package com.shyun.shop.service;

public interface FileService {

	//byte[] fileData - 업로드한 파일 데이터를 구한다 그걸 db에 저장
	String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception;

	void deleteFile(String filePath) throws Exception;

}