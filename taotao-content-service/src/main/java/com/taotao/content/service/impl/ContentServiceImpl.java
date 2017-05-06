package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.content.utils.JedisClient;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;

/**
 * 
 * @author luopa
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${CONTENT_CID}")
	private String CONTENT_CID;

	/**
	 * 分页查询
	 */
	@Override
	public EasyUIResult getContentList(Long categoryId, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(example);

		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		EasyUIResult result = new EasyUIResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public TaotaoResult saveContent(TbContent content) {
		try {
			Date date = new Date();
			content.setCreated(date);
			content.setUpdated(date);
			contentMapper.insert(content);
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(201, "添加失败!");
		}
	}

	/**
	 * 修改
	 */
	@Override
	public TaotaoResult editContent(TbContent content) {
		try {
			Date date = new Date();
			content.setUpdated(date);
			contentMapper.updateByPrimaryKeySelective(content);
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(201, "修改失败!");
		}
	}

	/**
	 * 删除
	 */
	@Override
	public TaotaoResult deleteContent(Long[] ids) {
		try {
			if (ids.length > 0) {
				for (Long id : ids) {
					contentMapper.deleteByPrimaryKey(id);
				}
			}
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(201, "删除失败!");
		}
	}

	@Override
	public List<TbContent> getContentLists(Long aD1_CID) {
		try {
			// 从redis中获取缓存
			String json = jedisClient.hget(CONTENT_CID, aD1_CID.toString());
			if (StringUtils.isNotBlank(json)) {
				long start = System.currentTimeMillis();
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				long end = System.currentTimeMillis();
				// 测试耗用时间
				System.out.println("redis取数据:" + (end - start) + "ms");
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long start = System.currentTimeMillis();
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(aD1_CID);
		List<TbContent> list = contentMapper.selectByExample(example);
		long end = System.currentTimeMillis();
		System.out.println("直接从数据库取数据:" + (end - start) + "ms");
		try {
			// 向redis中添加缓存
			jedisClient.hset(CONTENT_CID, aD1_CID.toString(), JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
