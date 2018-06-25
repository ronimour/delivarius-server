package com.delivarius.server.spring.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.data.domain.Persistable;

import com.delivarius.server.spring.domain.utils.PersistableUtils;

@Entity
public class ItemOrder implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne@JoinColumn 
	@NotNull
	private Order order;
	
	@ManyToOne
	@NotNull
	private Product product;
	
	@PositiveOrZero@NotNull
	private Integer amount;
	
	@Positive@NotNull
	private BigDecimal totalPrice;
	
	public ItemOrder() {}

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	@Override
	public boolean isNew() {
		return PersistableUtils.isNew(this);
	}

	@Override
	public boolean equals(Object obj) {
		return PersistableUtils.equalsPersistable(this, obj);
	}

}
