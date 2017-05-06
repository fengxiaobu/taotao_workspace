package com.taotao.search.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {
	//添加文档
	public void	 testDemo1() throws Exception, IOException {
		//创建SolrServ  CloudSolrServerer
		CloudSolrServer server=new CloudSolrServer("192.168.25.128:2182,192.168.25.128:2183,192.168.25.128:2184");
		//需要默认collection 搜索域
		server.setDefaultCollection("collection1");
		
		SolrInputDocument document=new SolrInputDocument();
		document.setField("id", "007");
		document.setField("item_title", "手机");
		document.setField("item_price", "7");
		server.add(document);
		server.commit();
	}
	//查询文档
	public void	 testDemo3() throws Exception, IOException {
		//创建SolrServ  CloudSolrServerer
		CloudSolrServer server=new CloudSolrServer("192.168.25.128:2182,192.168.25.128:2183,192.168.25.128:2184");
		//需要默认collection 搜索域
		server.setDefaultCollection("collection1");
		SolrQuery query=new SolrQuery();
		query.setQuery("*:*");
		query.set("df", "item_title");
		QueryResponse request=server.query(query);
		System.out.println(request.getResults().getNumFound());
	}
	//删除文档
	public void	 testDemo2() throws Exception, IOException {
		//创建SolrServ  CloudSolrServerer
		CloudSolrServer server=new CloudSolrServer("192.168.25.128:2182,192.168.25.128:2183,192.168.25.128:2184");
		//需要默认collection 搜索域
		server.setDefaultCollection("collection1");
		
		server.deleteByQuery("*:*");
		server.commit();
	}
}
