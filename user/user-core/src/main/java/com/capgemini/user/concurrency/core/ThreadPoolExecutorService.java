package com.capgemini.user.concurrency.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.capgemini.user.logging.core.LogPublisher;
import com.capgemini.user.logging.event.LogEventTypes;
import com.capgemini.user.logging.event.SimpleLogEvent;

@Component("threadPoolExecutorService")
public class ThreadPoolExecutorService {
	
	@Autowired @Qualifier("logPublisher") 
	private LogPublisher logPublisher;
	
	@Value("${concurrency.default.corePoolSize:2}")
	private String corePoolSize;
	
	@Value("${concurrency.default.maximumPoolSize:20}")
	private String maximumPoolSize;
	
	@Value("${concurrency.default.keepAliveTimeInMilliSecs:300000}")
	private String keepAliveTimeInMilliSecs;
	
	@Value("${concurrency.default.max.queuedTask:50000}")
	private String blockedQueueSize;
	
	@Value("${concurrency.default.threadTermination:5}")
	private String threadTerminationWaitTime;
	
	private ExecutorService executorService;
	
	@PostConstruct
	protected void initialize(){
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(Integer.valueOf(blockedQueueSize));
		CallerJoinRejectedExecutionHandler executionHandler = new CallerJoinRejectedExecutionHandler();
		executorService = new CommandCallbackThreadPoolExecutor(Integer.valueOf(corePoolSize), 
				Integer.valueOf(maximumPoolSize), Integer.valueOf(keepAliveTimeInMilliSecs), TimeUnit.MILLISECONDS, workQueue,executionHandler);
		((ThreadPoolExecutor)executorService).prestartAllCoreThreads();
		
	}
	
	@PreDestroy
	protected void cleanUp(){
		// give chance before killing the threads
		executorService.awaitTermination(Integer.valueOf(threadTerminationWaitTime), TimeUnit.SECONDS);
		forceShutdown();
	}
	
	public <T> Future<T> submit(CallableCommand<T> callableCommand){
		return executorService.submit(callableCommand);
	}
	
	public void shutdown(){
		executorService.shutdown();
	}
	
	public void forceShutdown(){
		executorService.shutdownNow();
	}
	
	private class CallerJoinRejectedExecutionHandler implements RejectedExecutionHandler{

		/**
		 * In case of rejection, it logs the task and run in caller thread
		 * instead of loosing the task. Other implementations can first try to
		 * pause for a 100 millisecond and then retry to run in separate
		 * thread, if still rejected then log it and run in current thread.
		 * Advance implementation can store the rejected task somewhere which
		 * can be retrieved later and process asynchronously instead of
		 * processing in caller thread synchronously.
		 */
		@Override
		public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
			logPublisher.publishLog(new SimpleLogEvent(String.format("Task was rejected %s",runnable), LogEventTypes.WARN.toString()));
			if (!executor.isShutdown()) {
				logPublisher.publishLog(new SimpleLogEvent(String.format("Task is executed in caller thread instead of loosing the task rejected %s",
						runnable), LogEventTypes.WARN.toString()));
				runnable.run();
			}
		}

	}
	
	private class CommandCallbackThreadPoolExecutor extends ThreadPoolExecutor {

		public CommandCallbackThreadPoolExecutor(int corePoolSize,
				int maximumPoolSize, long keepAliveTime, TimeUnit unit,
				BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
		}
		
		/**
		 * Do all common operation required across application here before starting
		 * execution in separate thread, example populate ThreadLocal, log the
		 * task, change any property of Thread etc.
		 */
		@Override
		protected void beforeExecute(Thread thread, Runnable runnable) {
			if(runnable instanceof CommandExecutionCallback<?>){
				logPublisher.publishLog(new SimpleLogEvent(String.format("Callback for beforeExecute() %s about to execute by Thread %s ",
						runnable, thread), LogEventTypes.DEBUG.toString()));
				((CommandExecutionCallback<?>)runnable).beforeExecute();
			}
			super.beforeExecute(thread, runnable);
		}
		
		/**
		 * Do all common operation required across application here before completion of
		 * execution in separate thread, example clear ThreadLocal, log the
		 * task, handle error scenarios, change any property of Thread etc.
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected void afterExecute(Runnable runnable, Throwable throwable) {
			super.afterExecute(runnable, throwable);
			if(throwable !=null){
				// TODO: Handle common exception scenario, may be just log and let callback process the exception
				logPublisher.publishLog(new SimpleLogEvent(String.format("Exception %s came after completing execution in separate thread for  %s ",
						throwable, runnable), LogEventTypes.DEBUG.toString()));
			}
			if(throwable instanceof CommandExecutionCallback<?>){
				Object obj = ((CallableCommand<?>)runnable).result();
				((CommandExecutionCallback)runnable).afterExecute(obj, throwable);
			}
		}
		
	}

}
