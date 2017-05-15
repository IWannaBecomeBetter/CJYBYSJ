package com.bs.common.exception;

import java.lang.reflect.InvocationTargetException;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String className = null;

	public ApplicationException() {

	}

	public ApplicationException(String msg) {
		super(msg);
	}

	public ApplicationException(Throwable throwable) {
		super(throwable);
	}

	public ApplicationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ApplicationException(String msg, Class cls, Throwable throwable) {
		super(msg, throwable);
		this.className = cls.getName();
	}

	public ApplicationException(String msg, String cls, Throwable throwable) {
		super(msg, throwable);
		this.className = cls;
	}

	public String getCauseMessage() {
		String className = "";
		if (this.className != null) {
			className = this.className + " : ";
		}
		if (getCause() == null)
			return "";
		if (getCause() instanceof InvocationTargetException) {
			return className
					+ ((InvocationTargetException) getCause())
							.getTargetException().getMessage();
		}
		return className + getCause().getMessage();
	}
}
