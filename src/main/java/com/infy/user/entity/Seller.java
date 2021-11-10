package com.infy.user.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.infy.user.dto.SellerDTO;

@Entity
@Table(name = "seller")
public class Seller {

	@Id
	private String sellerId;
	private String name;
	private String email;
	private Long phoneNumber;
	private String password;
	private Boolean isActive;

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public SellerDTO toDTO() {
		SellerDTO dto = new SellerDTO();
		dto.setEmail(email);
		dto.setIsActive(isActive);
		dto.setName(name);
//		dto.setPassword(password);
		dto.setPhoneNumber(phoneNumber);
		return dto;
	}

}