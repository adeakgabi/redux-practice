package com.b2international.redux;

import java.util.UUID;
import java.util.function.Consumer;

public interface ReduxStore<State, Action> {
	
	State getState();
	
	void dispatch();
	
	UUID subscribe(Consumer<State> subscriber);
	
	void unsubscribe(Consumer<State> subscriber);
	void unsubscribe(UUID subscriberID);
	

}
