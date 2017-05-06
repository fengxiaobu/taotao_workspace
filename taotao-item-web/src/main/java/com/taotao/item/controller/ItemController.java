package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemSerivce;

/**
 * 商品详情
 * 
 * @author luopa
 *
 */
@Controller
public class ItemController {

	@Autowired
	private ItemSerivce itemSerivce;

	@RequestMapping("/item/{itemId}")
	public String showInfo(@PathVariable Long itemId, Model model) {
		TbItem tbItem = itemSerivce.geTbItem(itemId);
		Item item = new Item(tbItem);
		TbItemDesc itemDesc = itemSerivce.geTbItemDesc(itemId);

		model.addAttribute("item", tbItem);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
}
