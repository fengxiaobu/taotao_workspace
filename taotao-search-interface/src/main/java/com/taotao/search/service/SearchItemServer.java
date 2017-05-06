package com.taotao.search.service;

import java.util.List;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.utils.TaotaoResult;

public interface SearchItemServer {
	TaotaoResult importAllItemToIndex() throws Exception;
	SearchResult searchResult(String querystring,Integer page,Integer rows) throws Exception;
}
