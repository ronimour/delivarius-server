package com.delivarius.spring.server.delivariusserver.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.spring.server.delivariusserver.domain.utils.PersistableUtils;

import edu.umd.cs.findbugs.annotations.NonNull;

@Entity
public class Store implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String name;

	@NotEmpty
	private String description;
	
	@NotEmpty
	private String picture;

	@ManyToOne
	@NotNull
	private Address address;
	
	private Boolean opens24hours;
	
	@NotNull
	@FutureOrPresent
	private LocalDateTime registrationDate;
	
	@OneToOne
	private WeekOpenTime weekOpenTime;
	
	@OneToMany(mappedBy="store")
	private List<ProductStock> productsStock;
	
	public Store() {
		this.productsStock = new ArrayList<>();
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Boolean getOpens24hours() {
		return opens24hours;
	}

	public void setOpens24hours(Boolean opens24hours) {
		this.opens24hours = opens24hours;
	}
	
	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public WeekOpenTime getWeekOpenTime() {
		return weekOpenTime;
	}

	public void setWeekOpenTime(WeekOpenTime weekOpenTime) {
		this.weekOpenTime = weekOpenTime;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public List<ProductStock> getProductsStock() {
		return productsStock;
	}

	public void setProductsStock(@NotNull List<ProductStock> productsStock) {
		this.productsStock = productsStock;
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
