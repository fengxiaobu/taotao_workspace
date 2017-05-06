package com.taotao.item.pojo;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.taotao.pojo.TbItem;

public class Item extends TbItem {

	public String[] getImages() {
		String image = this.getImage();
		if (image != null && !"".equals(image)) {
			return image.split(",");
		}
		return null;
	}

	public Item() {
	}

	public Item(TbItem tbItem) {
		this.setCid(tbItem.getCid());
		this.setBarcode(this.getBarcode());
		this.setCreated(tbItem.getCreated());
		this.setImage(tbItem.getImage());
		this.setNum(this.getNum());
		this.setPrice(tbItem.getPrice());
		this.setSellPoint(tbItem.getSellPoint());
		this.setStatus(this.getStatus());
		this.setUpdated(tbItem.getUpdated());
		this.setTitle(tbItem.getTitle());
	}

}
