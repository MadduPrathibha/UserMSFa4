package com.infy.user.repository;

import org.springframework.data.repository.CrudRepository;

import com.infy.user.entity.Seller;

public interface SellerRepository extends CrudRepository<Seller, String> {
	
	public Seller findByPhoneNumber(Long contancNumber);
	
	public Seller findByEmail(String email);
	
	public Seller findBySellerId(String id);

}
