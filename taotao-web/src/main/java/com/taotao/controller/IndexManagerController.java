package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.search.service.SearchItemServer;

/**
 * 索引库维护
 * 
 * @author luopa
 *
 */
@Controller
public class IndexManagerController {
	@Autowired
	private SearchItemServer searchItemServer;

	@RequestMapping("item/import")
	@ResponseBody
	public TaotaoResult importItem() throws Exception {

		return searchItemServer.importAllItemToIndex();
	}

}
