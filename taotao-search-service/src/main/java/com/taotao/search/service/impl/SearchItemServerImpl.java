package com.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.search.dao.ItemSearchDao;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.service.SearchItemServer;

@Service
public class SearchItemServerImpl implements SearchItemServer {

	@Autowired
	private SolrServer solrServer;
	@Autowired
	private ItemSearchDao itemSearchDao;
	@Autowired
	private ItemMapper itemMapper;

	@Override
	public TaotaoResult importAllItemToIndex() throws Exception {
		List<SearchItem> list = itemMapper.getItemList();

		for (SearchItem searchItem : list) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			document.addField("item_desc", searchItem.getItem_des());
			solrServer.add(document);
		}
		solrServer.commit();

		return TaotaoResult.ok();
	}

	@Override
	public SearchResult searchResult(String querystring, Integer page, Integer rows) throws Exception {
		SolrQuery query = new SolrQuery();
		query.set("df", "item_title");
		query.setQuery(querystring);
		if (page < 0)
			page = 1;
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style='color:red'>");
		query.setHighlightSimplePost("</em>");

		SearchResult result = itemSearchDao.searchResult(query);
		long recordCount = result.getRecordCount();
		long pagecount = recordCount / page;
		if (recordCount % page > 0) {
			pagecount++;
		}
		result.setPageCount(pagecount);
		return result;
	}

	/**
	 * 向索引库中添加文档。
	 * 
	 * @param itemId
	 * @return
	 * @throws Exception
	 */
	public TaotaoResult addItemResult(Long itemId) throws Exception {

		
		// 返回成功，返回TaotaoResult。
		return TaotaoResult.ok();
	}

}
