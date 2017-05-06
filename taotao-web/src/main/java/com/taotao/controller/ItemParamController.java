package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemParamItemService;

/**
 * 商品规格信息Controller
 * 
 * @author luopa
 *
 */
@Controller
public class ItemParamController {

	@Autowired
	private ItemParamItemService itemParamItemService;

	/**
	 * 加载商品规格
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/rest/item/param/item/query/{id}")
	@ResponseBody
	public TaotaoResult saveItem(@PathVariable Long id) {
		TaotaoResult taotaoResult = new TaotaoResult();
		taotaoResult.setStatus(200);
		taotaoResult.setData(itemParamItemService.geTbItemParamItem(id));
		return taotaoResult;
	}
}
