package dao;

import util.JdbcSqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Sqlite
{
  Connection c = null;

  Sqlite()
  {
    c = new JdbcSqlite().getJDBC();
    System.out.println("Opened database successfully");
  }
  //创建表：User
  public void createUser()
  {
    try {
      Statement stmt = c.createStatement();
      String sql = "CREATE TABLE USER " +
                   "(id TEXT PRIMARY KEY     NOT NULL," +
                   " passwd        TEXT)";
      stmt.executeUpdate(sql);
      stmt.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Table created successfully");
  }
  //创建表：TalkData
  public void createTalkData()
  {
    try {
      Statement stmt = c.createStatement();
      String sql = "CREATE TABLE TALK " +
                   "(title TEXT PRIMARY KEY     NOT NULL," +
                   "text      TEXT ," +
                   "author    TEXT ," +
                   "date      TEXT)";
      stmt.executeUpdate(sql);
      stmt.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Table created successfully");
  }
  //插入数据：User
  public void insert(String num, String pswd)
  {
    try {
      Statement stmt = c.createStatement();
      String sql = "INSERT INTO USER (id,passwd) " +
                   String.format("VALUES ('%s', '%s');", num, pswd);
      stmt.executeUpdate(sql);
      stmt.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Records created successfully");
  }
  //插入数据：TalkData
  public void insertTalk(String title, String text,String author, String date)
  {
    try {
      Statement stmt = c.createStatement();
      String sql = "INSERT INTO TALK (title,text,author,date)" +
                   String.format("VALUES ('%s', '%s','%s', '%s');", title, text,author,date);
      stmt.executeUpdate(sql);
      stmt.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Records created successfully");
  }
  //查询数据：User
  public List<String> select()
  {
	List<String> list = new ArrayList<String>();

    try {
      Statement stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM USER;" );
      while ( rs.next() ) {
    	 String id = rs.getString("id");
    	 String passwd  = rs.getString("passwd");

         list.add(id);
         list.add(passwd);
      }
      rs.close();
      stmt.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    
    System.out.println("Operation done successfully");
    return list;
  }
  //查询数据：Talk
  public List<String> selectTalk()
  {
	List<String> list = new ArrayList<String>();

    try {
      Statement stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM TALK;" );
      while ( rs.next() ) {
    	 String title = rs.getString("title");
    	 String text = rs.getString("text");
    	 String author = rs.getString("author");
    	 String date = rs.getString("date");

         list.add(title);
         list.add(text);
         list.add(author);
         list.add(date);
      }
      rs.close();
      stmt.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    
    System.out.println("Operation done successfully");
    return list;
  }
  //删除数据
  public void delete(String T, String ID) {
    try {
      Statement stmt = c.createStatement();
      String sql = String.format("DELETE from '%s' where title='%s';", T,ID);
      stmt.executeUpdate(sql);

      stmt.close();

    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Operation done successfully");
  }
}