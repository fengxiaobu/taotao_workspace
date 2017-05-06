package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * 内容分类Controller
 * @author luopa
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;

@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> gEasyUITreeNodes(@RequestParam(value = "id", defaultValue = "0") Long parentId) {

		return contentCategoryService.getContentCategorys(parentId);
	}

	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult createEasyUITreeNode(Long parentId, String name) {

		return contentCategoryService.createContentCategory(parentId, name);
	}

	@RequestMapping("/content/category/update")
	@ResponseBody
	public TaotaoResult updateEasyUITreeNode(Long id, String name) {

		return contentCategoryService.updateContentCategory(id, name);
	}

	@RequestMapping("/content/category/delete")
	@ResponseBody
	public TaotaoResult deleteEasyUITreeNode(Long id) {

		return contentCategoryService.deleteContentCategory(id);
	}

}
