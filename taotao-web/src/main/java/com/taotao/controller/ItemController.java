package com.taotao.controller;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.leveldb.replicated.groups.TextNodeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemSerivce;

/**
 * Item Controoler
 * 
 * @author luopa
 *
 */
@Controller
public class ItemController {
	@Autowired
	private ItemSerivce itemSerivce;
	@Autowired
	private JmsTemplate jmsTemplate;

	@Resource(name = "topicDestination_save")
	private Destination topicDestination_save;
	
	@Resource(name = "topicDestination_del")
	private Destination topicDestination_del;

	/**
	 * 查询商品信息
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIResult getItemList(Integer page, Integer rows) {
		EasyUIResult easyUIResult = itemSerivce.getItems(page, rows);
		return easyUIResult;
	}

	/**
	 * 删除商品信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public TaotaoResult deleteItem(Long[] ids) {
		itemSerivce.deleteItem(ids);
		for (final Long id : ids) {
			jmsTemplate.send(topicDestination_del, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					System.out.println("发送消息" + id);
					TextMessage textMessage = session.createTextMessage(id + "");
					return textMessage;
				}
			});
		}
		return TaotaoResult.ok();
	}

	/**
	 * 下架商品信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public TaotaoResult instockItem(Long[] ids) {

		itemSerivce.instockItem(ids);

		return TaotaoResult.ok();
	}

	/**
	 * 上架商品信息
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public TaotaoResult reshelfItem(Long[] ids) {

		itemSerivce.reshelfItem(ids);

		return TaotaoResult.ok();
	}

	/**
	 * 添加商品信息
	 * 
	 * @param item
	 *            商品信息
	 * @param desc
	 *            商品描述
	 * @return
	 */
	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult saveItem(TbItem item, String desc) {
		// 向ActiveMQ中发送消息 商品ID
		TaotaoResult taotaoResult = itemSerivce.saveItem(item, desc);
		final String msg = taotaoResult.getMsg();
		jmsTemplate.send(topicDestination_save, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(msg);
				return textMessage;
			}
		});
		return taotaoResult;
	}

	/**
	 * 加载商品描述
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/rest/item/query/item/desc/{id}")
	@ResponseBody
	public TaotaoResult saveItem(@PathVariable Long id) {

		TbItemDesc tbItemDesc = itemSerivce.geTbItemDesc(id);
		TaotaoResult taotaoResult = new TaotaoResult();
		taotaoResult.setStatus(200);
		taotaoResult.setData(tbItemDesc);
		return taotaoResult;
	}

	/**
	 * 更新商品信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/rest/item/update")
	@ResponseBody
	public TaotaoResult updateItem(TbItem item, String desc) {

		TaotaoResult taotaoResult = itemSerivce.updateItem(item, desc);

		return taotaoResult;
	}

}
