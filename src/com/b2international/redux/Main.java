package com.b2international.redux;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

import javaslang.Tuple;


public class Main {
	
	@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	public static void main(String[] args) {
		
		final Counter counter = new Counter();
		
	    System.out.println("Provide a number what you would like to increment.");
	    Scanner scanner = new Scanner(System.in);
	    int userInput = Integer.valueOf(scanner.nextLine());
		
		Store<Integer, String> storeWithoutMiddleware = Redux.createStore(10, counter.getReducer());
		
		Store<Integer, String> storeWithMiddleware = Redux.createStore(userInput, counter.getReducer(), counter.getMiddleware());
		
		System.out.println(storeWithMiddleware.getState());
		storeWithMiddleware.dispatch(Counter.INCREMENT);
		System.out.println("Current state of storeWithMiddleware: " + storeWithMiddleware.getState());
		
		System.out.println("Starting state of store: " + storeWithoutMiddleware.getState());
		Stream.of(1,2,3,4,5).forEach(e -> storeWithoutMiddleware.dispatch(Counter.DECREMENT));
		System.out.println("Current state of store after decrement 5 times " + storeWithoutMiddleware.getState());
		
		
		final Reducer<String, String> concatIsh = (action, state) -> "CONCAT".equals(action) ? state + "ish" : state;
		final Reducer<String, Integer> plus2 = (action, state) -> "PLUS".equals(action) ? state + 2 : state;
		
		final Reducer reducers = Redux.combineReducers(Tuple.of("string", concatIsh), Tuple.of("int", plus2));
		
		Map<String, Object> initialState = new HashMap<String, Object>() {{
			put("string", "blue");
			put("int", 0);
		}};
		
		Store<Map<String,Object>, String> store = Redux.createStore(initialState, reducers);
		
		store.dispatch("CONCAT");
		System.out.println(store.getState());
		
		store.dispatch("PLUS");
		System.out.println(store.getState());
		
		store.dispatch("CONCAT");
		System.out.println(store.getState());
		
		store.dispatch("PLUS");
		System.out.println(store.getState());
	}

}
