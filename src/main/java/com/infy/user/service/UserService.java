package com.infy.user.service;

import java.util.List;

import com.infy.user.dto.BuyerDTO;
import com.infy.user.dto.CartDTO;
import com.infy.user.dto.SellerDTO;
import com.infy.user.dto.WishlistDTO;
import com.infy.user.exception.UserMsException;

public interface UserService {

	public String buyerRegistration(BuyerDTO buyerDTO) throws UserMsException;

	public String sellerRegistration(SellerDTO sellerDTO) throws UserMsException;

	public String buyerLogin(String email, String password) throws UserMsException;

	public String sellerLogin(String email, String password) throws UserMsException;

	public BuyerDTO getBuyer(String id) throws UserMsException;

	public SellerDTO getSeller(String id) throws UserMsException;

	public String deleteBuyer(String id) throws UserMsException;

	public String deleteSeller(String id);

	public String addWishlist(String prodId, String buyerId);



	public String cartService(String prodId, String buyerId, Integer quantity) throws UserMsException;

	public List<CartDTO> getCartProducts(String id) throws UserMsException;

	public String removeFromCart(String buyerId, String prodId) throws UserMsException;

	public String updateRewardPoint(String buyerId, Float rewPoints) throws UserMsException;

	public String deactivateBuyer(String id) throws UserMsException;

	String deleteWishlist(WishlistDTO dto) throws UserMsException;

	public String deactiveSeller(String id) throws UserMsException;

	List<WishlistDTO> getWishlist(String buyerId) throws UserMsException;


}
