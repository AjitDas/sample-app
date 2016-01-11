package com.ajit.common.concurrency.core;

import java.util.List;

public interface ConcurrencyProcessor {
	
	public <T extends Command<P>, P> List<P> processAndWaitForComplete(List<T> workList);
	
	public <T extends Command<P>, P> List<P> processAndWaitForComplete(List<T> workList, long timeOut);
	
	public <T extends Command<P>, P> void processAndForget(List<T> workList);

}
