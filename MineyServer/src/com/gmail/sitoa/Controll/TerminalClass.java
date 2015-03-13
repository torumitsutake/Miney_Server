package com.gmail.sitoa.Controll;

import java.sql.SQLException;
import java.util.Scanner;

import com.gmail.sitoa.ConnectionWithClient.ServerSocketClass;
import com.gmail.sitoa.Datebase.DBConnectionClass;


public class TerminalClass implements Runnable{
	private static TerminalClass instance;
	Thread thread;
	TerminalClass(){
		System.out.println("TerminalOpen");
		 thread = new Thread(this);
		thread.start();
	}






	public static TerminalClass getInstance(){
		if(instance == null){
			instance = new TerminalClass();
		}
		return instance;

	}






	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);
		while(thread.isAlive()){
			Command(sc.next());
		}
		//処理部分




	}

	public void Command(String cmd){
		if(cmd.equals("serverstop")){
			ServerSocketClass ssc = ServerSocketClass.getInstance();
			ssc.stopSocket();
			DBConnectionClass dbc = DBConnectionClass.getInstance();
			dbc.dbdisconnect();
			System.exit(100);
		}
		if(cmd.equals("setquery")){
			Scanner sc1 = new Scanner(System.in);
			DBConnectionClass dbc = DBConnectionClass.getInstance();
			try {
				dbc.donotresult(sc1.nextLine());
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
		}

	}

}
