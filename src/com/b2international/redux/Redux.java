package com.b2international.redux;

import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

import javaslang.Tuple2;

public class Redux {
	 private static final PropertyUtilsBean props = new PropertyUtilsBean();

	    public static <State, Action> Store<State, Action> createStore(State initialState, Reducer<Action, State> reducer,
	                Middleware... middlewares) {
	        return Store.create(initialState, reducer, middlewares);
	    }

	    @SafeVarargs
	    public static <State> Reducer<Object, State> combineReducers(Tuple2<String, Reducer>... reducers) {
	        javaslang.collection.Map<String, Reducer> allReducers = javaslang.collection.HashMap.ofEntries(reducers);

	        return (action, state) -> {
	            if (state instanceof Map) {
	                Map<String, Object> s = (Map<String, Object>) state;
	                allReducers.forEach((a, r) -> s.put(a, r.apply(action, s.get(a))));
	            } else {
	                allReducers.forEach((a, r) -> {
	                    try {
	                        props.setProperty(state, a, r.apply(action, props.getNestedProperty(state, a)));
	                    } catch (Throwable e) {}
	                });
	            }
	            return state;
	        };
	    }

}
