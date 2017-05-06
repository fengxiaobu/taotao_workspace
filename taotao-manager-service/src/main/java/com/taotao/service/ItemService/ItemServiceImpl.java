package com.taotao.service.ItemService;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemSerivce;

/**
 * 商品信息Service
 * 
 * @author luopa
 *
 */
@Service
public class ItemServiceImpl implements ItemSerivce {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;

	/**
	 * 查询商品信息
	 */
	@Override
	public EasyUIResult getItems(Integer page, Integer rows) {
		// 设置分页信息
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(new TbItemExample());
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIResult easyUIResult = new EasyUIResult();
		easyUIResult.setTotal(pageInfo.getTotal());
		easyUIResult.setRows(list);
		return easyUIResult;
	}

	/**
	 * 删除商品信息
	 */
	@Override
	public void deleteItem(Long[] ids) {
		if (ids.length > 0 && ids != null) {
			for (Long id : ids) {
				itemMapper.deleteByPrimaryKey(id);
			}
		}
	}

	/**
	 * 下架商品
	 */
	@Override
	public void instockItem(Long[] ids) {
		if (ids.length > 0 && ids != null) {
			for (Long id : ids) {
				TbItem item = new TbItem();
				item.setId(id);
				item.setStatus((byte) 2);
				itemMapper.updateByPrimaryKeySelective(item);
			}
		}

	}

	/**
	 * 上架商品
	 */
	@Override
	public void reshelfItem(Long[] ids) {
		if (ids.length > 0 && ids != null) {
			for (Long id : ids) {
				TbItem item = new TbItem();
				item.setId(id);
				item.setStatus((byte) 1);
				itemMapper.updateByPrimaryKeySelective(item);
			}
		}
	}

	/**
	 * 新增商品信息
	 */
	public TaotaoResult saveItem(TbItem item, String desc) {
		TaotaoResult taotaoResult = null;
		try {
			taotaoResult = new TaotaoResult();
			long id = IDUtils.genItemId();
			Date date = new Date();
			item.setId(id);
			item.setStatus((byte) 1);
			item.setCreated(date);
			item.setUpdated(date);
			itemMapper.insert(item);
			// 商品描述
			TbItemDesc itemDesc = new TbItemDesc();
			itemDesc.setItemId(id);
			itemDesc.setItemDesc(desc);
			itemDesc.setCreated(date);
			itemDesc.setUpdated(date);
			itemDescMapper.insert(itemDesc);
			taotaoResult.setMsg(id + "");
			taotaoResult.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(2001, "添加失败!");
		}

		return taotaoResult;
	}

	/**
	 * 根据商品ID查询商品信息
	 */
	@Override
	public TbItem geTbItem(Long id) {

		return itemMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据商品ID查询商品信息
	 */
	@Override
	public TbItemDesc geTbItemDesc(Long id) {

		return itemDescMapper.selectByPrimaryKey(id);
	}

	/**
	 * 修改商品信息
	 */
	@Override
	public TaotaoResult updateItem(TbItem item, String desc) {
		TaotaoResult taotaoResult = new TaotaoResult();
		try {
			Date date = new Date();
			item.setUpdated(date);

			TbItemDesc itemDesc = new TbItemDesc();
			itemDesc.setUpdated(date);
			itemDesc.setItemDesc(desc);

			itemDescMapper.updateByPrimaryKeySelective(itemDesc);
			itemMapper.updateByPrimaryKeySelective(item);
		} catch (Exception e) {
			taotaoResult.setStatus(201);
			taotaoResult.setMsg("修改失败!");
		}
		taotaoResult.setStatus(200);

		return taotaoResult;
	}

}
