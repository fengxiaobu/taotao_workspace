package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

	EasyUIResult getContentList(Long categoryId, Integer page, Integer rows);

	TaotaoResult saveContent(TbContent content);

	TaotaoResult editContent(TbContent content);

	TaotaoResult deleteContent(Long[] ids);

	List<TbContent> getContentLists(Long aD1_CID);
	

}
