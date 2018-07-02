package com.delivarius.server.spring.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class ItemOrderDto implements DataTransferObject{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotNull
	private ProductDto productDto;
	
	private Integer amount;
	
	private BigDecimal totalPrice;
	
	private Long orderId;
	
	public ItemOrderDto() {}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductDto getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
}
