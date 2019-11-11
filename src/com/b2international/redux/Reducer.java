package com.b2international.redux;

import javaslang.Function2;

@FunctionalInterface
public interface Reducer<Action, State> extends Function2<Action, State, State> {

}
