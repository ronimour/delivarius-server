package com.delivarius.server.spring.domain.utils;

import org.springframework.lang.NonNull;

import com.delivarius.server.spring.domain.Order;
import com.delivarius.server.spring.domain.StatusOrder;

public class OrderUtils {
	
	public static boolean isOrderOpen(@NonNull Order order) {
		if(order.getHistory().size() == 1 && order.getHistory().get(0).getStatus().equals(StatusOrder.OPENED)) {
			return true;
		} else {
			return false;
		}
	}

}
