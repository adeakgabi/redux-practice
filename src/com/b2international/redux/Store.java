package com.b2international.redux;

import java.awt.Desktop.Action;
import java.lang.Thread.State;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.UUID;
import java.util.function.Consumer;

import javaslang.collection.List;

//@SuppressWarnings("hiding")
public class Store<State, Action> extends Observable implements ReduxStore<State, Action>{
	
	State currentState;
	final Reducer<Action, State> reducer;
	final Map<UUID, Consumer<State>> consumers = new HashMap<>();
	final Consumer<Action> middlewareStack;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <State, Action> Store<State, Action> create(State initialState, Reducer<Action, State> reducer,
			Middleware[] middlewares) {
		return new Store<>(initialState, reducer, middlewares);
	}
	
	@SuppressWarnings("unchecked")
	private Store(State initialState, Reducer<Action, State> reducer, Middleware<State, Action>... middlewares) {
	    this.currentState = initialState;
	    this.reducer = reducer;

	    if (middlewares == null || middlewares.length == 0) {
	        middlewareStack = null;
	    } else {
	        middlewareStack = (action) -> List.of(middlewares)
	            .fold((s1, a1, m) -> internalDispatch(action),
	                (m1, m2) -> (c, d, e) -> m2.accept(this, action, m1)
	            )
	            .accept(this, action, null);
	    }
	}
	
	public State getState() {
		return currentState;
	}
	
	public void dispatch(Action action) {
		if(middlewareStack == null) {
			this.internalDispatch(action);
		} else {
			middlewareStack.accept(action);
		}
	}
	
	public void internalDispatch(Action action) {
		 reduce(action);
		 notifySubscribers();
	}
	
	private void reduce(Action action) {
		this.currentState = reducer.apply(action, this.currentState);
	}
	
	private void notifySubscribers() {
	    consumers.values().parallelStream()
	        .forEach(subscriber -> subscriber.accept(currentState));
	}

	public UUID subscribe(Consumer<State> subscriber) {
	    final UUID uuid = UUID.randomUUID();
	    this.consumers.put(uuid, subscriber);
	    return uuid;
	}

	public void unsubscribe(Consumer<State> subscriber) {
	    this.consumers.entrySet().stream()
	        .filter(e -> e.getValue().equals(subscriber)).findFirst()
	        .ifPresent(e -> this.consumers.remove(e.getKey()));
	}

	public void unsubscribe(UUID subscriberId) {
	    this.consumers.remove(subscriberId);
	}

	@Override
	public void dispatch() {
		
	}

}
