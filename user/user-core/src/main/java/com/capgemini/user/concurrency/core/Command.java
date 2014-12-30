package com.capgemini.user.concurrency.core;

public interface Command<T> {

	public T execute();
}
