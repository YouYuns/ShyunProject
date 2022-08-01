package com.shyun.shop.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.tomcat.util.file.ConfigurationSource.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.multipart.MultipartFile;

import com.shyun.shop.dto.FileData;

import lombok.extern.log4j.Log4j2;
@Log4j2
public class MyFileUtils {

	ApplicationContext ac;
	
	
	public static FileData upload(MultipartFile multipartFile, String url) {
		
		String newName=null;
		String orgName = multipartFile.getOriginalFilename();
		//resource아래있는 상속
		ClassPathResource cpr = new ClassPathResource("static" + url);//"static/images/visual
		
		try {
			File location = cpr.getFile();
			multipartFile.transferTo(new File(location, orgName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FileData.builder()
				.url(url).orgName(orgName).newName(newName)
				.build();
	}


	public static FileData upload(List<MultipartFile> reviewImgFile, String url) {
		// TODO Auto-generated method stub
		return null;
	}

}
