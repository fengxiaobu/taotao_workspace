package com.taotao.test;

import org.junit.Test;

import com.taotao.utils.FastDFSClient;

/**
 * 
 * 测试图片上传
 * 
 * @author luopa
 *
 */
public class TestFastDFS {
	@Test
	public void testFastDFS() throws Exception {
		FastDFSClient fastDFSClient = new FastDFSClient(
				"C:/developers/taotao_workspace/taotao-web/src/main/resources/resource/fast_dfs.properties");

		String path = fastDFSClient.uploadFile("C:/Users/luopa/Desktop/4K壁纸/4K (1).jpg", "jpg", null);
		System.out.println(path);
	}
}
