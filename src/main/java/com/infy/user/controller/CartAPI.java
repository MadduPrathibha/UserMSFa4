package com.infy.user.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.infy.user.dto.CartDTO;
import com.infy.user.dto.ProductDTO;
import com.infy.user.exception.UserMsException;
import com.infy.user.service.UserService;
import com.infy.user.utility.ErrorInfo;

@CrossOrigin
@RestController
@RequestMapping(value = "/cart")
public class CartAPI {

	@Autowired
	private UserService userService;

	@Value("${product.uri}")
	String prodUri;

	// Kafka implementation

	/*
	 * Key pair value given in kafka
	 */
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	/*
	 * A topic has to be decided
	 */
	private static final String TOPIC = "simple";

	@GetMapping(value = "/{buyerId}")
	public ResponseEntity<List<CartDTO>> getProductListFromCart(@PathVariable String buyerId) throws UserMsException {

		List<CartDTO> list = userService.getCartProducts(buyerId);

		return new ResponseEntity<>(list, HttpStatus.ACCEPTED);

	}

	@GetMapping(value="/checkout/{buyerId}")
	public ResponseEntity<String> checkout(@PathVariable String buyerId) throws UserMsException{
		List<CartDTO> list = userService.getCartProducts(buyerId);
//		list.forEach(item->{
//			String message = String.format("%s %s %s", item.getBuyerId(), item.getProdId(), item.getQuantity());
//			
//		});


		StringJoiner joiner = new StringJoiner(", ");
		
		list.forEach(item->{
			joiner.add(String.format("%s %s %s", item.getBuyerId(), item.getProdId(), item.getQuantity()));
		});
		
		
		
		kafkaTemplate.send(TOPIC, joiner.toString());
		return ResponseEntity.ok("Order placed Successfully");
	}

	// DEMO for KAFKA

	@GetMapping("/publish/{message}")
	public String post(@PathVariable("message") final String message) {

		kafkaTemplate.send(TOPIC, message);

		System.out.println("published");

		return "Published successfully";
	}

	@PostMapping(value = "")
	public ResponseEntity<String> addProductToCart(@Valid @RequestBody CartDTO cartDTO) throws UserMsException {

		System.out.println(cartDTO);

		ProductDTO product;
		try {
			product = new RestTemplate().getForObject(prodUri + "/productMS/product/productId/" + cartDTO.getProdId(),
					ProductDTO.class);
		} catch (Exception e) {
			throw new UserMsException("Product not found", HttpStatus.NOT_FOUND.value());
		}

		String msg = userService.cartService(product.getProdId(), cartDTO.getBuyerId(), cartDTO.getQuantity());

		return new ResponseEntity<>(msg, HttpStatus.ACCEPTED);

	}

	@DeleteMapping(value = "/{buyerId}/{prodId}")
	public ResponseEntity<String> removeFromCart(@PathVariable String buyerId, @PathVariable String prodId)
			throws UserMsException {

		String msg = userService.removeFromCart(buyerId, prodId);

		return new ResponseEntity<>(msg, HttpStatus.OK);

	}

	@ExceptionHandler(UserMsException.class)
	public ResponseEntity<ErrorInfo> exceptionHandlerForUserMS(UserMsException exception) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(exception.getStatusCode());
		errorInfo.setTimestamp(LocalDateTime.now());

		String errorMsg = exception.getMessage();
		errorInfo.setErrorMessage(errorMsg);

		return new ResponseEntity<>(errorInfo, HttpStatus.resolve(exception.getStatusCode()));
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<ErrorInfo> exceptionHandler(MethodArgumentNotValidException exception) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setTimestamp(LocalDateTime.now());

		String errorMsg = exception.getBindingResult().getAllErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.joining(", "));
		errorInfo.setErrorMessage(errorMsg);

		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<ErrorInfo> exceptionHandlerForEntity(ConstraintViolationException exception) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		errorInfo.setTimestamp(LocalDateTime.now());

		String errorMsg = exception.getConstraintViolations().stream().map(x -> x.getMessage())
				.collect(Collectors.joining(", "));

		errorInfo.setErrorMessage(errorMsg);

		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}

}
