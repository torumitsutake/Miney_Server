package com.gmail.sitoa.Controll;

import com.gmail.sitoa.ConnectionWithClient.ServerSocketClass;
import com.gmail.sitoa.Datebase.DBConnectionClass;

public class ServerStartClass {
	public static  TerminalClass terminal;
	public static ServerSocketClass ssc;
	public static DBConnectionClass db;

	public static void main(String[] args) {
		terminal = TerminalClass.getInstance();
		ssc = ServerSocketClass.getInstance();
		db = DBConnectionClass.getInstance();
		
		


	}

}
