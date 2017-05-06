package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

/**
 * ContentCategoryService 内容管理Service
 * 
 * @author luopa
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	/**
	 * 分类查询
	 */
	@Override
	public List<EasyUITreeNode> getContentCategorys(Long parentid) {

		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentid);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> treeNodes = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			treeNodes.add(node);
		}
		return treeNodes;
	}

	/**
	 * 创建新节点
	 */
	@Override
	public TaotaoResult createContentCategory(Long parentId, String name) {
		TbContentCategory tbContentCategory = new TbContentCategory();
		Date date = new Date();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setStatus(1);
		tbContentCategory.setCreated(date);
		tbContentCategory.setUpdated(date);
		tbContentCategory.setName(name);
		// 是否是父节点
		tbContentCategory.setIsParent(false);
		// 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
		tbContentCategory.setSortOrder(1);
		// 状态。可选值:1(正常),2(删除)
		tbContentCategory.setStatus(1);

		contentCategoryMapper.insert(tbContentCategory);
		// 3、判断父节点的isparent是否为true，不是true需要改为true。
		TbContentCategory tCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!tCategory.getIsParent()) {
			tCategory.setIsParent(true);
			// 更新父节点
			contentCategoryMapper.updateByPrimaryKey(tCategory);
		}
		return TaotaoResult.ok(tbContentCategory);
	}

	/**
	 * 删除节点(递归删除)
	 */
	@Override
	public TaotaoResult deleteContentCategory(Long id) {
		try {
			TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
			contentCategoryMapper.deleteByPrimaryKey(id);
			if (category.getIsParent()) {
				TbContentCategoryExample example = new TbContentCategoryExample();
				Criteria criteria = example.createCriteria();
				criteria.andParentIdEqualTo(category.getId());
				List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
				for (TbContentCategory tbContentCategory : list) {
					if (tbContentCategory.getIsParent()) {
						deleteContentCategory(tbContentCategory.getId());
					}
					contentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
				}
			}
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(201, "删除失败!");
		}
	}

	/**
	 * 修改
	 */
	@Override
	public TaotaoResult updateContentCategory(Long id, String name) {
		try {
			TbContentCategory record = new TbContentCategory();
			record.setId(id);
			record.setName(name);
			contentCategoryMapper.updateByPrimaryKeySelective(record);
			return TaotaoResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(201, "修改失败!");
		}
	}

}
