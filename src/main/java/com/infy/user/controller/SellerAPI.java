package com.infy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infy.user.dto.SellerDTO;
import com.infy.user.exception.UserMsException;
import com.infy.user.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/seller")
public class SellerAPI {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<SellerDTO> getBuyerDetail(@PathVariable String id) {
		try {
			SellerDTO dto = userService.getSeller(id);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} catch (UserMsException e) {
			e.printStackTrace();
			String msg = "Seller not found";
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg, e);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteBuyerAccount(@PathVariable String id) {

		String msg;
		try {
			msg = userService.deleteSeller(id);

			return new ResponseEntity<>(msg, HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();

			msg = "Cannot delete user";
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg, e);
		}

	}

	@PutMapping(value = "/deactivate/{id}")
	public ResponseEntity<String> deactivateBuyerAccount(@PathVariable String id) {
		String msg;
		try {
			msg = userService.deactiveSeller(id);

			return new ResponseEntity<>(msg, HttpStatus.OK);
		} catch (UserMsException e) {

			e.printStackTrace();

			msg = "Cannot deactivate user";
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg, e);
		}

	}

}
