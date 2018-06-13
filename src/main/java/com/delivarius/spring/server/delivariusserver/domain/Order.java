package com.delivarius.spring.server.delivariusserver.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Persistable;

import com.delivarius.spring.server.delivariusserver.domain.utils.PersistableUtils;

@Entity
@Table(name="orders")
public class Order implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@NotNull
	private Store store;
	
	@ManyToOne
	@NotNull
	private User user;
	
	@OneToMany(mappedBy="order", cascade=CascadeType.ALL)
	@NotEmpty
	private List<ItemOrder> items;
	
	@OneToMany(mappedBy="order", cascade=CascadeType.ALL)
	@NotEmpty
	private List<HistoryStatusOrder> history;
	
	public Order() {
		this.history = new ArrayList<>();
		this.items = new ArrayList<>();
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ItemOrder> getItems() {
		return items;
	}

	public void setItems(@NotNull List<ItemOrder> items) {
		this.items = items;
	}

	public List<HistoryStatusOrder> getHistory() {
		return history;
	}

	public void setHistory(@NotNull List<HistoryStatusOrder> history) {
		this.history = history;
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
