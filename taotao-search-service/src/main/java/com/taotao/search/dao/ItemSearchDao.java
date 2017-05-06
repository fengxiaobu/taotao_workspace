package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;

/**
 * 商品索引查询
 * 
 * @author luopa
 *
 */
@Repository
public class ItemSearchDao {

	@Autowired
	protected SolrServer solrServer;

	/**
	 * 条件查询
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public SearchResult searchResult(SolrQuery query) throws Exception {
		SearchResult result = new SearchResult();
		QueryResponse response = solrServer.query(query);
		SolrDocumentList solrDocumentList = response.getResults();
		result.setRecordCount(solrDocumentList.getNumFound());
		List<SearchItem> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem item = new SearchItem();
			item.setId(Long.parseLong(solrDocument.get("id").toString()));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((Long) solrDocument.get("item_price"));
			// 取高亮显示
			String title = null;
			Map<String, Map<String, List<String>>> map = response.getHighlighting();
			List<String> list = map.get(solrDocument.get("id")).get(solrDocument.get("item_title"));
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			itemList.add(item);
		}
		result.setItemList(itemList);
		return result;

	}
}
