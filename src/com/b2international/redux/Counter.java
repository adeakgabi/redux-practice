package com.b2international.redux;

import static io.vavr.API.*;


public class Counter {
	
	static final String INCREMENT = "INC";
	static final String DECREMENT = "DEC";
	
	final Reducer<String, Integer> reducer = (action, state) -> {
		return state + Match(action).of(
				Case($(INCREMENT), 1),
				Case($(DECREMENT), -1),
				Case($(), 0)
				);
	};

	final Middleware<Integer, String> middleware = (store, action, next) -> {
		System.out.println("Before: " + store.getState());
		next.accept(store, action, null);
		System.out.println("After: " + store.getState());
	};
	
    public void counter() {
        // This is our store with its initial state of zero and the reducer seen above
        Store<Integer, String> store = Redux.createStore(0, reducer);

        // dispatch an INC action
        store.dispatch(INCREMENT);
        store.getState(); // 1

        // dispatch an DEC action
        store.dispatch(DECREMENT);
        store.getState(); // 0
    }
	
	public Reducer<String, Integer> getReducer() {
		return reducer;
	}

	public Middleware<Integer, String> getMiddleware() {
		return middleware;
	}
    
}
