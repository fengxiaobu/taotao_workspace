package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

@Controller
public class ContentContoller {

	@Autowired
	private ContentService contentService;

	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIResult getContentList(Long categoryId, Integer page, Integer rows) {

		return contentService.getContentList(categoryId, page, rows);
	}

	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult saveContent(TbContent content) {

		return contentService.saveContent(content);
	}

	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public TaotaoResult editContent(TbContent content) {

		return contentService.editContent(content);
	}

	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult deleteContent(Long[] ids) {

		return contentService.deleteContent(ids);
	}
}
