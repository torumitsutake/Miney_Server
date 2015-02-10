package com.gmail.sitoa.Datebase;


public class DBConnectionClass {

	private static DBConnectionClass instance;









	public static DBConnectionClass getInstance(){
		if(instance == null){
			instance = new DBConnectionClass();
		}
		return instance;

	}

}
