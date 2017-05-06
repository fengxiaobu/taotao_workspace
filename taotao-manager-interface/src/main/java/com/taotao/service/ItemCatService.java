package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCat;

public interface ItemCatService {

	List<EasyUITreeNode> getCatList(long parentId);

	TbItemCat getTbItemCat(Long id);
}
