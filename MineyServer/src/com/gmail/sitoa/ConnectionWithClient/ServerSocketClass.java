package com.gmail.sitoa.ConnectionWithClient;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketClass {
	private static ServerSocketClass instance;
public void startSocket(){
	try{
		ss = new ServerSocket(25560);
		
		while(!ss.isClosed()){
			
			Socket client = ss.accept();
			
			ClientConnectedClass ccc = new ClientConnectedClass(client);
			
		}
		
		
	}catch(Exception err) {
		err.printStackTrace();
	}
	
	
	
	
	
}

public void stopSocket(){
	
}

private ServerSocket ss;







	public static ServerSocketClass getInstance(){
		if(instance == null){
			instance = new ServerSocketClass();
		}
		return instance;

	}


}
