package com.taotao.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemParamItemService;

@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItemParamItem geTbItemParamItem(Long id) {

		return itemParamItemMapper.selectByPrimaryKey(id);
	}

}
