package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchItemServer;

@Controller
public class SearchItemController {
	
	@Autowired
	private SearchItemServer searchItemServer;
	
	@Value("${ITEM_ROWS}")
	private Integer ITEM_ROWS;
	@RequestMapping("/search")
	public String searchItem(@RequestParam("q") String querystring ,@RequestParam(defaultValue="1") Integer page,Model model) throws Exception{
		querystring=new String(querystring.getBytes("iso8859-1"), "utf-8");
		SearchResult result=searchItemServer.searchResult(querystring, page, ITEM_ROWS);
		model.addAttribute("query", querystring);
		model.addAttribute("itemList", result.getItemList());
		model.addAttribute("totalPages", result.getPageCount());
		model.addAttribute("page", page);
		
		return "search";
	}
}
