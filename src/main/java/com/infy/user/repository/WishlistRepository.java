package com.infy.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.infy.user.entity.Wishlist;
import com.infy.user.utility.CustomPK;


public interface WishlistRepository extends CrudRepository<Wishlist, CustomPK> {
	
	
	public List<Wishlist> findByCustomIdBuyerId(String buyerId);
	

}