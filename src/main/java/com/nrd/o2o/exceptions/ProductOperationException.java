package com.nrd.o2o.exceptions;

public class ProductOperationException extends RuntimeException {
	private static final long serialVersionUID = -4627164144084099311L;

	public ProductOperationException(String msg) {
		super(msg);
	}
}
