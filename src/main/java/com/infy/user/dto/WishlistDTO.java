package com.infy.user.dto;

import com.infy.user.entity.Wishlist;
import com.infy.user.utility.CustomPK;

public class WishlistDTO {
	
	private String buyerId;
	private String prodId;
	
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	public Wishlist toEntity() {
		Wishlist entity = new Wishlist();
		CustomPK pk = new CustomPK();
		pk.setBuyerId(buyerId);
		pk.setProdId(prodId);
		entity.setCustomId(pk);
		return entity;
	}

}
