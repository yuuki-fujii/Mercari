package com.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.SearchItemListService;

@Controller
@RequestMapping("/download_csv")
public class CsvController {
	
	@Autowired
	private SearchItemListService searchItemListService;
	
	@RequestMapping("")
	public void downloadCsv(HttpServletResponse response) {
		String encodedFileName = null;
		try {
			encodedFileName = URLEncoder.encode("メルカリ.csv", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"" );
		
		try (PrintWriter pw = response.getWriter()) {
			List <Item> itemList = searchItemListService.getAllItems();
			StringBuilder outputStringBuilder = new StringBuilder();
			outputStringBuilder.append("name,price,category,brand,condition,image,\r\n");
			
			for (Item item : itemList) {
				String name = item.getName();
				String price = String.valueOf(item.getPrice());
				String category = item.getCategoryNameAll();
				String brand = item.getBrandName();
				String condition = String.valueOf(item.getCondition());
				String image = item.getImage();
				
				// CSVファイル内部に記載する形式で文字列を設定
				outputStringBuilder.append(name + "," + price + "," + category + "," + brand + ",");
				outputStringBuilder.append(condition +  "," + image + ",\r\n");
			}
			// CSVファイルに書き込み
			pw.print(outputStringBuilder.toString());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
