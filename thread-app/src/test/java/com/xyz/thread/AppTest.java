package com.xyz.thread;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */

public class AppTest {

	@Before
	public void setUp() {

	}

	@Test
	public void testMain() throws InterruptedException, ExecutionException {
		ThreadFactory mock = new CustomThreadFactory();
		ExecutorService executorService = Executors.newCachedThreadPool(mock);
		App.setService(executorService);
		App.main(null);
		assertEquals(true, executorService.isTerminated());// or whatever assert
															// we want to apply.
	}

	@After
	public void tearDown() {

	}

	class CustomThreadFactory implements ThreadFactory {
		public Thread newThread(Runnable r) {
			return new Thread(r);
		}
	}
}
