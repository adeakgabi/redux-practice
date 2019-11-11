package com.b2international.redux;

@FunctionalInterface
public interface Middleware<State, Action> extends TriConsumer<ReduxStore<State, Action>, Action, Middleware<State, Action>> {

}
