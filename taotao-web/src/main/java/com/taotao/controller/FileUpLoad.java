package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;
import com.taotao.utils.FastDFSClient;

/**
 * 图片上传Controller
 * 
 * @author luopa
 *
 */
@Controller
public class FileUpLoad {
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	/*
	 * @RequestMapping("/pic/upload")
	 * 
	 * @ResponseBody public Map fileUpLoad_map(MultipartFile uploadFile) { try {
	 * // 获取原文件名 String originalFilename = uploadFile.getOriginalFilename(); //
	 * 获取文件类型 String extName =
	 * originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
	 * 
	 * FastDFSClient fastDFSClient = new
	 * FastDFSClient("classpath:resource/fast_dfs.properties"); String path =
	 * fastDFSClient.uploadFile(uploadFile.getBytes(), extName); String url =
	 * IMAGE_SERVER_URL + path; Map map = new HashMap<>(); map.put("error", 0);
	 * map.put("url", url); return map;
	 * 
	 * } catch (Exception e) { e.printStackTrace(); Map map = new HashMap<>();
	 * map.put("error", 1); map.put("message", "上传失败!"); return map;
	 * 
	 * } }
	 */
	@RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
	@ResponseBody
	public String fileUpLoad_String(MultipartFile uploadFile) {
		try {
			// 获取原文件名
			String originalFilename = uploadFile.getOriginalFilename();
			// 获取文件类型
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/fast_dfs.properties");
			String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			String url = IMAGE_SERVER_URL + path;
			Map map = new HashMap<>();
			map.put("error", 0);
			map.put("url", url);

			return JsonUtils.objectToJson(map);

		} catch (Exception e) {
			e.printStackTrace();
			Map map = new HashMap<>();
			map.put("error", 1);
			map.put("message", "上传失败!");
			return JsonUtils.objectToJson(map);

		}
	}
}
