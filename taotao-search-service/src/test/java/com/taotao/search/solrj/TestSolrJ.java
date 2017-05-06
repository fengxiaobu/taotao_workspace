package com.taotao.search.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class TestSolrJ {

	public void testSolrJAddDocument() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.134:8080/solr");

		SolrInputDocument solrInputDocument = new SolrInputDocument();
		solrInputDocument.addField("id", "test001");
		solrInputDocument.addField("item_title", "测试商品");
		solrInputDocument.addField("item_price", 100);

		solrServer.add(solrInputDocument);
		solrServer.commit();

	}

	public void testSolrJSearchDocument() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.134:8080/solr");

		SolrQuery query = new SolrQuery();
		query.setQuery("三星");
		query.set("df", "item_title");
		query.setStart(0);
		query.setRows(30);
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePost("</em>");
		query.setHighlightSimplePre("<em>");

		QueryResponse response = solrServer.query(query);

		SolrDocumentList document = response.getResults();

		System.out.println("总数据:" + document.getNumFound());

		for (SolrDocument solrDocument : document) {
			System.out.println(solrDocument.get("id"));
			String title = null;
			Map<String, Map<String, List<String>>> map = response.getHighlighting();
			List<String> list = map.get(solrDocument.get("id")).get("item_title");
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			System.out.println(title);
			System.out.println(solrDocument.get("item_price"));
		}
	}

}
