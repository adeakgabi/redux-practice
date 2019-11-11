package com.b2international.redux;

import java.util.ArrayList;
import java.util.List;

public class Number {
	
	String string = "number";
	int i = 1;
	List<String> list = new ArrayList<>();
	
	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	@Override
	public String toString() { return "Number(" + string + ", " + i + ", " + list +")"; }

}
