package com.gmail.sitoa.ConnectionWithClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;

import com.gmail.sitoa.ConnectionWithClient.NetworkCatchEvent.MessageListener;
import com.gmail.sitoa.Datebase.DBConnectionClass;
import com.gmail.sitoa.Datebase.DBMakeQueryClass;



class NetworkCatchEvent extends EventObject{

	private ClientConnectedClass source;
	private String name;
	private String value;

	public NetworkCatchEvent(ClientConnectedClass source,String name,String value) {
		super(source);
		this.source= source;
		this.name = name;
		this.value = value;
	}

	public ClientConnectedClass getClient(){
		return source;
	}
	public String getName(){
		return name;
	}
	public String getValue(){
		return value;
	}

	interface MessageListener extends EventListener{
		void messageThrow(NetworkCatchEvent e);

	}





}



public class ClientConnectedClass implements Runnable,MessageListener{
	private Socket sc;
	String GameID = "";
	String owner = "unknown";
	String ServerID = "";
	boolean activation = false;
	int gamecategory = 0;
	private ArrayList<MessageListener> messageListeners;

	ClientConnectedClass(Socket socket){
messageListeners = new ArrayList<MessageListener>();
		sc = socket;
		addMessageListener(this);
		Thread thred = new Thread(this);
		thred.start();


	}
	public void reachedMessage(String name, String value) {
		NetworkCatchEvent event = new NetworkCatchEvent(this, name, value);
		for(int i = 0 ; i < messageListeners.size() ; i++ ) {
		messageListeners.get(i).messageThrow(event);
		}
		}

	private void addMessageListener(MessageListener l) {
		messageListeners.add(l);

	}
	//指定したメッセージリスナをこのオブジェクトから解除する
	public void removeMessageListener(MessageListener l) {
	messageListeners.remove(l);
	}

	public void setcategory(int i){
		gamecategory = i;
	}

	public void setGameID(String id){
		GameID = id;
	}
	public void setServerID(String id){
		ServerID = id;
	}

	public void setOwner(String id){
		owner = id;
	}
	public void setactivation(String activate){
		if(activate.equals("true")){
			activation = true;
		}else{
			activation = false;
		}


	}


	@Override
	public void run() {
		try {
			InputStream input = sc.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			while(!sc.isClosed()){
				String line =  reader.readLine();
				//[command].[value]
				System.out.println("NetworkInput:"+line);
				String[] lines = line.split(" ");
						String Command = lines[0];
						String value = lines[1];
						reachedMessage(Command,value);


			}


		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	@Override
	public void messageThrow(NetworkCatchEvent e) {
		String msgType = e.getName();
		String msgvalue = e.getValue();
		if(msgType.equals("close")){
			try {
				close();
			} catch (IOException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
		}
		//サーバー別型コイン
		if(msgType.equals("addCoin")){
			addCoin(msgvalue);
		}
		if(msgType.equals("addMny")){
			addMny(msgvalue);
		}
		if(msgType.equals("deletaMny")){
			deletaMny(msgvalue);
		}
		if(msgType.equals("deletaCoin")){
			deletaCoin(msgvalue);
		}
		if(msgType.equals("setinfo")){
			sendMessage(setting(msgvalue));
		}


	}

	public void close() throws IOException{
		messageListeners.clear();
		sc.close();
	}

	public void sendMessage(String str){
		try{
			OutputStream output = sc.getOutputStream();
			PrintWriter write = new PrintWriter(output);
			write.println(str);
			write.flush();
			System.out.println(str);


		}catch(Exception err){

		}

	}

	public boolean addCoin(String value){
		String[] lines = value.split("-");
		String player = lines[0];
		String amount = lines[1];
		Client cl = new Client(player);
		cl.checkjoin(GameID+"_"+ServerID);







		return false;
	}

	public boolean addMny(String value){
		String[] lines = value.split("-");
		String player = lines[0];
		String amount = lines[1];


		return false;

	}public boolean deletaCoin(String value){
		String[] lines = value.split("-");
		String player = lines[0];
		String amount = lines[1];



		return false;
	}

	public boolean deletaMny(String value){
		String[] lines = value.split("-");
		String player = lines[0];
		String amount = lines[1];

		return false;

	}

	public String setting(String value){
		String[] lines = value.split("-");
		String gameid = lines[0];
		String serverid = lines[1];
		String owner = "unknown";
		if(lines.length >= 3){
			owner = lines[2];
		}
		setGameID(gameid);
		setServerID(serverid);
		DBConnectionClass dbc = DBConnectionClass.getInstance();
		String query = DBMakeQueryClass.getGameInfo(gameid, serverid);
		try {
			ResultSet result = dbc.doresult(query);
			if(!result.next()){
				query = DBMakeQueryClass.setGameinfo(gameid, serverid, owner);

				try {
					dbc.donotresult(query);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				query = DBMakeQueryClass.createGameTable(gameid, serverid);
				try{
					dbc.donotresult(query);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			while(result.next()){
			owner = result.getString("owner");
			setOwner(owner);
			setactivation(result.getString("Activation"));
			setcategory(result.getInt("GameCategory"));
			}

			result.getStatement().getConnection().setAutoCommit(true);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		System.out.println(ServerID + "ServerSetting Complete!  Ready for Use");

		return "setinfo accept";

	}








}
