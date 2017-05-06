package com.taotao.service;

import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemSerivce {
	EasyUIResult getItems(Integer page, Integer rows);

	TaotaoResult saveItem(TbItem item, String desc);
	
	TbItem geTbItem(Long id);

	void deleteItem(Long[] ids);

	void instockItem(Long[] ids);

	void reshelfItem(Long[] ids);

	TbItemDesc geTbItemDesc(Long id);

	TaotaoResult updateItem(TbItem item, String desc);
}
