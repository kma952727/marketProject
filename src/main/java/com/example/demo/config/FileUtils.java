package com.example.demo.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileUtils {
	
	private static final String UPLOADPATH = "/Users/cat95/Documents/workspace-spring-tool-suite-4-4.10.0.RELEASE"
			+ "/MarketProject/src/main/resources/static/product_image";
	
	
	public LocalDateTime StringToDate(String StringDate) {
		String[] imageNames =  StringDate.split("/");
		LocalDateTime endTime = 
				LocalDateTime.of(Integer.parseInt(imageNames[0]),
						Integer.parseInt(imageNames[1]), Integer.parseInt(imageNames[2]), 0, 0, 0);
		return endTime;
	}
	
	public void saveFileToDisk(MultipartFile[] files) {
		
		OutputStream out = null;
		PrintWriter printWriter = null;
		String fileName;

			try {
				for(MultipartFile image : files) {
					fileName = image.getOriginalFilename();
					byte[] bytes = image.getBytes();
					File file = new File(UPLOADPATH + "/" +fileName);
					out = new FileOutputStream(file);
					out.write(bytes);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(out != null) out.close();
					if(printWriter != null) printWriter.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		
	}
}
