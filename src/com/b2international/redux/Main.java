package com.b2international.redux;

import static io.vavr.API.*;

import java.util.stream.Stream;

public class Main {
	
	public static void main(String[] args) {
		
		final Counter counter = new Counter();
		
		Store<Integer, String> store = Redux.createStore(10, counter.getReducer());
		
		Store<Integer, String> storeWithMiddleware = Redux.createStore(0, counter.getReducer(), counter.getMiddleware());
		
		System.out.println(storeWithMiddleware.getState());
		storeWithMiddleware.dispatch(Counter.INCREMENT);
		storeWithMiddleware.dispatch(Counter.INCREMENT);
		storeWithMiddleware.dispatch(Counter.INCREMENT);
		storeWithMiddleware.dispatch(Counter.INCREMENT);
		System.out.println("Current state of storeWithMiddleware: " + storeWithMiddleware.getState());
		
		System.out.println("Starting state of store: " + store.getState());
		Stream.of(1,2,3,4,5).forEach(e -> store.dispatch(Counter.DECREMENT));
		System.out.println("Current state of store after decrement 5 times " + store.getState());
		

	}

}
