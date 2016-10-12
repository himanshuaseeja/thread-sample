package com.xyz.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.xyz.services.ServiceA;
import com.xyz.services.ServiceAA;
import com.xyz.services.ServiceB;
import com.xyz.services.ServiceC;

/**
 * Main class -- entry point.
 *
 */
public class App 
{
	private static ExecutorService service;
	
	public static void setService(ExecutorService customService) {
		App.service = customService;
	}
	//The entire code above is exercised for junit only..
	
	public static void main( String[] args ) throws InterruptedException, ExecutionException
    {
		//the below condition will always be executed if running from main..in case of junit a diff service will be created based on mocked thread factory.
		if(service == null) {
			service =  Executors.newFixedThreadPool(2);
		} 
		
		//code below ensures that 2 operations are executing parallely
    	Future<String> outputFromOperationAA = service.submit(new OperationAA());
    	Future<String> outputFromOperationB = service.submit(new OperationB());
    	
    	service.shutdown();
    	while (!service.isTerminated()) {
    		// doNothing.. this block is to ensure that every thread has finished execution
    	}
    	
    	//Step 3 is getting executed here
    	ServiceC serC = new ServiceC();//or some web service
    	String outputFromC = serC.returnFinalOutcome(outputFromOperationAA.get(), outputFromOperationB.get());
    	System.out.println(outputFromC);
    }
    
	/*
	 * {non-Javadoc}
	 * This class performs step 1 of problem statement
	 */
    public static class OperationAA implements Callable<String> {

		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			ServiceA serA = new ServiceA();//or some web service
			String outputFromA = serA.returnSomething();
			
			ServiceAA serAA = new ServiceAA();//or some web service
			String outputFromAA = serAA.returnSomething(outputFromA);
			
			return outputFromAA;
		}
    	
    }
    /*
	 * {non-Javadoc}
	 * This class performs step 2 of problem statement
	 */
    public static class OperationB implements Callable<String> {

		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			ServiceB serB = new ServiceB();//or some web service
			
			return serB.returnSomethingElse();
		}
    	
    }
}
