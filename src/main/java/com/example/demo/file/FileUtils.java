package com.example.demo.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.ProductImage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileUtils {
	private static final String UPLOADPATH_SERVER = "/kma95277/tomcat/webapps/product_image/";
	private static final String DEVELOP_UPLOADPATH = "/product_image/";
//	private static final String UPLOADPATH = "/Users/cat95/Documents/workspace-spring-tool-suite-4-4.10.0.RELEASE"
//	+ "/MarketProject/src/main/resources/static/product_image/";
	
	/**
	 * db에 저장할수있게 테이블에 매칭되는 모델에 
	 * 값을 할당하는 함수입니다.
	 * 
	 * @param images 상품을 등록할때 같이등록한 이미지 파일들입니다.
	 * @return
	 */
	public List<ProductImage> convertImageToModel(MultipartFile[] images) {
		List<ProductImage> productImages = new ArrayList<ProductImage>();
		
		for(MultipartFile image : images) {
			
			ProductImage productImage = new ProductImage();
			String originalName = image.getOriginalFilename();
			//랜덤값을 생성합니다.(중복이름 방지)
			String serverName = UUID.randomUUID().toString().replace("-", "") + originalName;
			
			productImage.setOriginalImageName(image.getOriginalFilename());
			productImage.setSize(image.getSize());
			productImage.setServerImageName(DEVELOP_UPLOADPATH + serverName);
			
			productImages.add(productImage);
		}
		return productImages;
	}
	
	/**
	 * 상품등록시 등록된 사진들을 서버에 저장합니다.
	 * 
	 * @param files 상품사진배열입니다.
	 * @param imageList 'convertImageToModel'에서 반환한 값입니다.
	 */
	public void saveFileToDisk(MultipartFile[] files, List<ProductImage> imageList) {
		
		OutputStream out = null;
		PrintWriter printWriter = null;

		int count = 0;
		
			try {
				for(MultipartFile image : files) { 
					ProductImage imageVO = imageList.get(count++);
					String realPath = imageVO.getServerImageName().replaceAll(DEVELOP_UPLOADPATH, UPLOADPATH_SERVER);
							
					byte[] bytes = image.getBytes();
					File file = new File(realPath);
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
