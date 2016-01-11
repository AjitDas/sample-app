package com.ajit.common.concurrency.core;

public interface Command<T> {

	public T execute();
}
