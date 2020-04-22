package com.example.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.domain.Item;
import com.example.helper.DownloadHelper;
import com.example.service.SearchItemListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Controller
public class CsvController {
	
	@Autowired
	private SearchItemListService searchItemListService;
	
	
	@Autowired
	private DownloadHelper downloadHelper;
	
	
	/**
     * CsvMapperで、csvを作成する。
     * @return csv(String)
     * @throws JsonProcessingException
     */
	public String getCsvText() throws JsonProcessingException {
		CsvMapper mapper = new CsvMapper();
		//文字列にダブルクオートをつける
		mapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true);
		//ヘッダをつける
		CsvSchema schema = mapper.schemaFor(Item.class).withHeader();
		// 商品情報をDBから取得
		List <Item> itemList = searchItemListService.getAllItems();
		
		return mapper.writer(schema).writeValueAsString(itemList);
	}
	
	/**
     * csvをダウンロードする。
     * @param response
     * @return
     * @throws IOException
     */
	@RequestMapping(value = "/download/csv", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download() throws IOException {
		HttpHeaders headers = new HttpHeaders();
        downloadHelper.addContentDisposition(headers, "メルカリ.csv");
        return new ResponseEntity<>(getCsvText().getBytes("MS932"), headers, HttpStatus.OK);
	}
}
