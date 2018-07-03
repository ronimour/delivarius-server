package com.delivarius.server.spring.domain.exception;

public class NegativeAmountException extends Exception {
	public NegativeAmountException(Throwable e) {
		initCause(e);
	}

}
