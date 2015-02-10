package com.gmail.sitoa.Controll;


public class TerminalClass {
	private static TerminalClass instance;









	public static TerminalClass getInstance(){
		if(instance == null){
			instance = new TerminalClass();
		}
		return instance;

	}

}
