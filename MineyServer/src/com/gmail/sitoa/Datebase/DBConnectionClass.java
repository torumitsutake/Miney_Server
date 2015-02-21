package com.gmail.sitoa.Datebase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnectionClass {

	private static DBConnectionClass instance;
    Connection conn = null;
	DBConnectionClass(){
		try {
			connectDB();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}



	public static DBConnectionClass getInstance(){
		if(instance == null){
			instance = new DBConnectionClass();
		}
		return instance;

	}

	public void connectDB() throws SQLException{
		 final String driverName = org.apache.derby.jdbc.EmbeddedDriver.class.getCanonicalName();
		 final String dbName = "Mineydb";
		 final String connectionURL = "jdbc:derby:" + dbName + ";create=true";
		  try {
		      // ドライバをロードする
		      Class.forName(driverName);
		      // DB接続オブジェクトを参照する変数

		      try {
		        // DB接続オブジェクトを取得
		        conn = DriverManager.getConnection(connectionURL);
		        System.out.println("DBConnect!");
		        // DBが初期化されているかどうかを確認
		        if (!wasDbInitialized(conn)) {
		          // 初期化されていない場合は初期化処理が必要

		          // アプリで使用するあれこれのDBオブジェクトを定義
		          // ...

		          // 最後に、初期化判断に使用しているLOGテーブルを作成
		          final Statement s = conn.createStatement();
		          s.execute("create table LOG (" +
		              " ID        BIGINT    generated always as identity constraint LOG_PK primary key" +
		              ",TIMESTAMP TIMESTAMP with default CURRENT_TIMESTAMP" +
		              ",MESSAGE   VARCHAR(2000)" +
		              ")");
		        }



		      } catch (SQLException e) {
		        // 接続に失敗
		        System.out.println(e.getMessage());

		      }

		    } catch (java.lang.ClassNotFoundException e) {
		      // ドライバのロードに失敗
		      System.out.println(e.getMessage());
		    }

		  }

		  public static boolean wasDbInitialized(Connection conn) throws SQLException {
		    // チェック用クエリが返す結果セットを参照するための変数
		    ResultSet rs = null;
		    try {
		      // データベースが初期化されているかどうかを検証するためUPDATE文を発行
		      final Statement s = conn.createStatement();
		      // LOGテーブルの件数をカウント
		      // ※ここではこれ以上のチェックをしないことにする（テーブルのあるなしのみで判断することにする）
		      rs = s.executeQuery("select count(1) from LOG");

		    } catch (SQLException e) {
		      // 例外がスローされた。エラーコードを確認して処理を分岐

		      // SQLステートコードを取得。
		      final String state = e.getSQLState();

		      // ステートコードで処理を分岐。
		      if (state.equals("42X05")) {
		        // テーブルが存在しない。データベースはまだ初期化されていない。
		        return false;
		      } else if (state.equals("42X14") || state.equals("42821")) {
		        // テーブル定義が不正。テーブルは存在するも想定した定義ではない...
		        throw e;
		      } else {
		        // その他の予期せぬ例外が発生...
		        throw e;
		      }

		    } finally {
		      // ともかくもResultSetをクローズ
		      if (rs != null) rs.close();
		    }
		    // テーブルが存在した。（＝すでに初期化されている。）
		    return true;
		  }


		  public  void donotresult(String query) throws SQLException{
			  Statement stat = conn.createStatement();
			  try{
				  stat.execute(query);
			  }catch(SQLException se){
				  System.out.println(se);

			  }finally{
				  stat.close();
			  }

		  }

		  public  ResultSet doresult(String query) throws SQLException{
			  Statement stat = conn.createStatement();
			  ResultSet result = null;
			  try{
				   result = stat.executeQuery(query);
			  }catch(SQLException se){
				  System.out.println(se);

			  }finally{
				  stat.close();
			  }
			  return result;
		  }
		  
		  public boolean dbdisconnect(){
			  try {
				conn.close();
				return true;
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				  return false;
			}
			  
			  
			  
		  }


		}