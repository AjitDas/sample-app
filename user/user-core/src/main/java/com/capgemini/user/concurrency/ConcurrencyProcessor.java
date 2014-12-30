package com.capgemini.user.concurrency;

import java.util.List;

import com.capgemini.user.concurrency.core.Command;

public interface ConcurrencyProcessor {
	
	public <T extends Command<P>, P> List<P> processAndWaitForComplete(List<T> workList);
	
	public <T extends Command<P>, P> List<P> processAndWaitForComplete(List<T> workList, long timeOut);
	
	public <T extends Command<P>, P> void processAndForget(List<T> workList);

}
