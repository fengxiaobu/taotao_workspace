package com.test.page;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class TestPage {

	
	public void page() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-dao.xml");
		// 获得Mapper的代理对象
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);

		PageHelper.startPage(1, 20);
		TbItemExample tItemExample = new TbItemExample();

		List<TbItem> list = itemMapper.selectByExample(tItemExample);
		// 取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		System.out.println("总记录数:"+pageInfo.getTotal());
		System.out.println("总页数:"+pageInfo.getPages());
		System.out.println("当前页:"+pageInfo.getPageNum());
		System.out.println("每页显示:"+pageInfo.getPageSize());

	}
}
