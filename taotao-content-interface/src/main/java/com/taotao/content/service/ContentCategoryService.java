package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.utils.TaotaoResult;

public interface ContentCategoryService {
		
	List<EasyUITreeNode> getContentCategorys(Long parentid);

	TaotaoResult createContentCategory(Long parentId, String name);

	TaotaoResult deleteContentCategory(Long id);

	TaotaoResult updateContentCategory(Long id, String name);
}
