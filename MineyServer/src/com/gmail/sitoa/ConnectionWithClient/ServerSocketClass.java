package com.gmail.sitoa.ConnectionWithClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketClass implements Runnable{
	private static ServerSocketClass instance;
	ServerSocketClass (){
		Thread thread = new Thread(this);
		thread.start();
	}
public void startSocket(){
	try{
		ss = new ServerSocket(25560);
		System.out.println("Socketopen!");
		while(!ss.isClosed()){

			Socket client = ss.accept();

			ClientConnectedClass ccc = new ClientConnectedClass(client);

		}


	}catch(Exception err) {
		err.printStackTrace();
	}





}

public void stopSocket(){
	try {
		ss.close();
		System.out.println("Server socket close! Miney-Server Unworking.");
	} catch (IOException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	}

}

private ServerSocket ss;







	public static ServerSocketClass getInstance(){
		if(instance == null){
			instance = new ServerSocketClass();
		}
		return instance;

	}
	@Override
	public void run() {
		startSocket();
	}


}
