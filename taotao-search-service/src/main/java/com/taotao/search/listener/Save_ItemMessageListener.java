package com.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.common.pojo.SearchItem;
import com.taotao.search.mapper.ItemMapper;

/**
 * MessageListener实现类
 * 
 * @author luopa
 *
 */
@Repository
public class Save_ItemMessageListener implements MessageListener {
	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	protected SolrServer solrServer;

	@Override
	public void onMessage(Message message) {

		try {
			TextMessage textMessage = null;
			Long itemId = null;
			if (message instanceof TextMessage) {
				textMessage = (TextMessage) message;
				itemId = Long.parseLong(textMessage.getText());
				System.out.println("保存or修改" + itemId);
				SearchItem searchItem = itemMapper.getSearchItem(itemId);
				SolrInputDocument document = new SolrInputDocument();
				// 使用SolrServer对象写入索引库。
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				document.addField("item_desc", searchItem.getItem_des());
				// 向索引库中添加文档。
				solrServer.add(document);
				solrServer.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
