package com.delivarius.server.spring.domain.helper;

import java.time.LocalDateTime;

import com.delivarius.server.spring.domain.HistoryStatusOrder;
import com.delivarius.server.spring.domain.Order;
import com.delivarius.server.spring.domain.StatusOrder;
import com.delivarius.server.spring.domain.User;

public class HistoryStatusOrderHelper {

	public static HistoryStatusOrder getHistoryForOpen(Order order, User user) {
		return createHistory(order, user, StatusOrder.OPENED);
	}

	public static HistoryStatusOrder getHistoryForRegister(Order order, User user) {
		return createHistory(order, user, StatusOrder.REGISTERED);
	}

	public static HistoryStatusOrder getHistoryForAccept(Order order, User user) {
		return createHistory(order, user, StatusOrder.ACCEPTED);
	}

	public static HistoryStatusOrder getHistoryForDeny(Order order, User user) {
		return createHistory(order, user, StatusOrder.DENIED);
	}

	public static HistoryStatusOrder getHistoryForCancel(Order order, User user) {
		return createHistory(order, user, StatusOrder.CANCELED);
	}

	public static HistoryStatusOrder getHistoryForStartDelivery(Order order, User user) {
		return createHistory(order, user, StatusOrder.ON_DELIVERY);
	}

	public static HistoryStatusOrder getHistoryForDelivered(Order order, User user) {
		return createHistory(order, user, StatusOrder.DELIVERED);
	}

	public static HistoryStatusOrder getHistoryForClose(Order order, User user) {
		return createHistory(order, user, StatusOrder.CLOSED);
	}

	private static HistoryStatusOrder createHistory(Order order, User user, StatusOrder status) {
		HistoryStatusOrder history = new HistoryStatusOrder();
		history.setOrder(order);
		history.setUser(user);
		history.setRegistrationDate(LocalDateTime.now());
		history.setStatus(status);
		return history;
	}

}
