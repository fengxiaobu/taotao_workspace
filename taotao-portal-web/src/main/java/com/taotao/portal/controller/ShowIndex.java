package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.utils.Ad1Node;

/**
 * 显示前台首页
 * 
 * @author luopa
 *
 */
@Controller
public class ShowIndex {

	@Autowired
	private ContentService contentService;

	@Value("${AD1_CID}")
	private Long AD1_CID;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;

	@RequestMapping("/index")
	public String showIndex(Model model) {

		List<TbContent> list = contentService.getContentLists(AD1_CID);
		List<Ad1Node> aList = new ArrayList<>();
		for (TbContent content : list) {
			Ad1Node node = new Ad1Node();
			node.setAlt(content.getTitle());
			node.setHeight(AD1_HEIGHT);
			node.setHeightB(AD1_HEIGHT_B);
			node.setWidth(AD1_WIDTH);
			node.setWidthB(AD1_WIDTH_B);
			node.setHref(content.getUrl());
			node.setSrc(content.getPic());
			node.setSrcB(content.getPic2());

			aList.add(node);
		}
		model.addAttribute("ad1", JsonUtils.objectToJson(aList));
		return "index";
	}
}
