package com.ajit.common.concurrency.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("executorConcurrencyProcessor")
public class ExecutorConcurrencyProcessor implements ConcurrencyProcessor {
	
	@Value("${concurrency.default.max.wait.milliseconds:300000}")
	private String defaultMaxWait;
	
	@Autowired @Qualifier("threadPoolExecutorService")
	private ThreadPoolExecutorService executorService;
	
	@Override
	public <T extends Command<P>, P> List<P> processAndWaitForComplete(List<T> workList) {
		long timeOut = Long.valueOf(defaultMaxWait);
		return processAndWaitForComplete(workList, timeOut);
	}

	@Override
	public <T extends Command<P>, P> List<P> processAndWaitForComplete(List<T> workList, long timeOut) {
		if(workList == null || workList.isEmpty()){
			return Collections.<P>emptyList();
		}
		List<Future<P>> futureList = new ArrayList<>(workList.size());
		CountDownLatch countDownLatch = new CountDownLatch(workList.size());
		for(T workItem : workList){
			CallableCommand<P> callableCommand = null;
			if(workItem instanceof CallbackAwareCommand){
				callableCommand = new CallbackAwareCallableCommand<>((CallbackAwareCommand<P>)workItem, countDownLatch);
			}else if(workItem instanceof Command){
				callableCommand = new CallableCommand<>(workItem, countDownLatch);
			}
			Future<P> future = executorService.submit(callableCommand);
			futureList.add(future);
		}
		countDownLatch.await(timeOut, TimeUnit.MILLISECONDS);
		List<P> resultList = new ArrayList<>(workList.size());
		for(Future<P> future:futureList){
			// bug in AspectJ while softening in case of parameterized return type hence put in try-catch
			try{
				resultList.add(future.get());
			}catch(Exception e){
				throw new IllegalStateException(e);
			}
		}
		return resultList;
	}

	@Override
	public <T extends Command<P>, P> void processAndForget(List<T> workList) {
		if(workList == null || workList.isEmpty()){
			return ;
		}
		List<Future<P>> futureList = new ArrayList<>(workList.size());
		CountDownLatch countDownLatch = new CountDownLatch(workList.size());
		for(T workItem : workList){
			CallableCommand<P> callableCommand = null;
			if(workItem instanceof CallbackAwareCommand){
				callableCommand = new CallbackAwareCallableCommand<>((CallbackAwareCommand<P>)workItem, countDownLatch);
			}else if(workItem instanceof Command){
				callableCommand = new CallableCommand<>(workItem, countDownLatch);
			}
			Future<P> future = executorService.submit(callableCommand);
			futureList.add(future);
		}
	}

}
