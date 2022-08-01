package com.shyun.shop.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shyun.shop.service.FileService;

import lombok.extern.java.Log;



//파일 업로드 구현
//File Upload-> FileSERVER에 저장하면 MD5체크섬값의 파일명으로 SERVER저장 
//->  File정보를 mysql에 기록 id ,orgFilename : 원본파일명 / filename :server에 저장되는 파일명 /  uploadPath : 경로

@Service
@Log
public class FileServiceImpl implements FileService {
	//byte[] fileData - 업로드한 파일 데이터를 구한다 그걸 db에 저장
	@Override
	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
		UUID uuid = UUID.randomUUID();
		//UUID 서로다른개체들을 구별하기위해서 이름을 부여할때 사용
		
		
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		String savedFileName = uuid.toString() + extension;
		String fileUploadFullUrl = uploadPath + "/" + savedFileName;
		
		
		//FileOutputStream : 바이트 단위의 출력을 내보내는 클래스 생성자로 파일이 저장될위치와 파일의이름을 넘겨 
		//파일에 쓸 파일 출력스트림을 만든다.
		FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
		
		
		fos.write(fileData); //업로드된 파일 이름 반환
		fos.close();
		
		return savedFileName;
		
	}
	
	@Override
	public void deleteFile(String filePath) throws Exception{
		//파일이 저장된 경로를 이용하여 파일 객체 생성
		File deleteFile = new File(filePath);
		
		if(deleteFile.exists()) {
			deleteFile.delete();
			log.info("파일을 삭제하였습니다.");
		}else {
			log.info("파일이 존재하지 않습니다.");
		}
	}
	
}
