package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 页面跳转Controller
 * @author luopa
 *
 */
@Controller
public class PageController {
	/**
	 * 首页显示
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}

	/**
	 * 请求URL页面
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}

}
