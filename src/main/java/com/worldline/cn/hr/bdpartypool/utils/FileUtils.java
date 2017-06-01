package com.worldline.cn.hr.bdpartypool.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;

public class FileUtils {

	private FileUtils(){}
	
	public static InputStream getInputStream(String filePath) throws IOException {
		if(filePath.startsWith("classpath:")) {
			return new ClassPathResource(filePath.substring(10)).getInputStream();
		} else {
			return new FileInputStream(filePath);
		}
	}
}
