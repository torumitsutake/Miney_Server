package com.gmail.sitoa.Datebase;


public class DBMakeQueryClass {

	public static String getGameInfo(String gameid,String serverid){
		final String table = "GameInfo";
		String query= "SELECT * FROM "+table+" WHERE gameid = '"+gameid+"' AND serverid = '"+serverid+"'";
		return query;
	}
	public static String setGameinfo(String gameid,String serverid,String owner){
		String query = "INSERT INTO GameInfo(GameID,ServerID,Activation,Owner,GameCategory) VALUES ('"+gameid+"', '"+serverid+"','false','"+owner+"',0)";
		return query;
	}
	public static String createGameTable(String game,String server){
		String out = "";
		out= "CREATE TABLE "+game+"_"+server+"(JoinPlayer varchar(100),Coin int,options varchar(500)) ";
		return out;
	}
	public static String getPlayerInfo(String UUID){
		String query = "SELECT * FROM "+UUID;
		return query;
	}
	public static String createPlayerInfo(String UUID){
		String query = "CREATE TABLE "+UUID+"(JoinGame varchar(255)";
		return query;
	}

	public static String setgametoplayer(String gameid_serverid,String UUID){
		String query = "INSERT INTO "+UUID+"("+gameid_serverid+")";
		return query;
	}

}
