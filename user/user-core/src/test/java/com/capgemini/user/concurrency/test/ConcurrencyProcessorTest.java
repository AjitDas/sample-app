package com.capgemini.user.concurrency.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.user.concurrency.ConcurrencyProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContextTest.xml")
public class ConcurrencyProcessorTest {

	@Autowired
	private ConcurrencyProcessor concurrencyProcessor;
	
	@BeforeClass
	public static void setup(){
		System.setProperty("ENV_NAME", "TEST");
	}
	
	@Test
	public void testConcurrency(){
		List<SimpleCommand> simpleCommands = new ArrayList<>();
		List<String> uuids = new ArrayList<>();
		for(int i=0;i<10;i++){
			String uuid = UUID.randomUUID().toString();
			uuids.add(uuid);
			simpleCommands.add(new SimpleCommand(uuid));
		}
		
		List<String> returnList = concurrencyProcessor.processAndWaitForComplete(simpleCommands);
		boolean equalUuids = (uuids.size() == returnList.size()) && uuids.containsAll(returnList);
		
		Assert.assertTrue(equalUuids);
	}
}
