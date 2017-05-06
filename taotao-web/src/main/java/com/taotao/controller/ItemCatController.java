package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemCatService;

/**
 * 商品类型管理Controller
 * 
 * @author luopa
 *
 */
@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;

	/**
	 * 获取商品类型
	 * 
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> gEasyUITreeNode(@RequestParam(value = "id", defaultValue = "0") Long parentId) {

		List<EasyUITreeNode> list = itemCatService.getCatList(parentId);
		return list;
	}

}
