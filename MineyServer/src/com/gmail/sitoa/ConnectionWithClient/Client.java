package com.gmail.sitoa.ConnectionWithClient;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.gmail.sitoa.Datebase.DBConnectionClass;
import com.gmail.sitoa.Datebase.DBMakeQueryClass;

public class Client {
	String UUID;
	String name;
	ArrayList<String> gamename = new ArrayList<String>();
	ArrayList<Date> tss = new ArrayList<Date>();
	DBConnectionClass dbc = DBConnectionClass.getInstance();


	Client(String uuids){
	UUID = uuids;
	String query = DBMakeQueryClass.getPlayerInfo(UUID);
	try {
		ResultSet result = dbc.doresult(query);
		if(!result.next()){
			query = DBMakeQueryClass.createPlayerInfo(UUID);
			dbc.donotresult(query);
		}
		while(result.next()){
			String game =  result.getString("JoinGame");
			gamename.add(game);
		Date ts = result.getDate("LastLogin");
		tss.add(ts);
		}
		result.getStatement().getConnection().setAutoCommit(true);

	} catch (SQLException e) {
		e.printStackTrace();
	}
	}

	public boolean checkjoin(String gameid_serverid){
		if(gamename.contains(gameid_serverid)){
			return true;
		}else{

			return false;
		}


	}





}
