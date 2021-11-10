package com.infy.user.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.infy.user.utility.CustomPK;

@Entity
@Table(name = "cart")
public class Cart {

	@EmbeddedId
	private CustomPK customPK;

	@NotNull(message = "Quantity must not be empty")
	@Column(name="quantity")
	private Integer quantity;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public CustomPK getCustomPK() {
		return customPK;
	}

	public void setCustomPK(CustomPK customPK) {
		this.customPK = customPK;
	}
}
