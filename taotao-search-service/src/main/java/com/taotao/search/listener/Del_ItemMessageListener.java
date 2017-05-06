package com.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * MessageListener实现类
 * 
 * @author luopa
 *
 */
@Repository
public class Del_ItemMessageListener implements MessageListener {

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
				System.out.println("删除"+itemId);
				solrServer.deleteById(itemId + "");
				solrServer.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
