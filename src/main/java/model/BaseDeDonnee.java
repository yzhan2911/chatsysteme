package model;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class BaseDeDonnee {
     private final Lock lock = new ReentrantLock();
    public static String url = "jdbc:sqlite:bdd.db";
    public static record dataMessage(Date time, String sender, String recever, String message){}
    public BaseDeDonnee(){
        create_new_basededonne();
        create_new_table();
    }


    public void create_new_basededonne(){
        DatabaseMetaData meta;
        try (Connection connection=DriverManager.getConnection(url)){
            meta =connection.getMetaData();
            System.out.println("[Model] BaseDeDonnee: nom de driver: "+meta.getDriverName());
            System.out.println("[Model] BaseDeDonnee: creation base de donnee reussir");
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }
    
   
    public void create_new_table(){
        String sql = "CREATE TABLE IF NOT EXISTS history(\n"
        + "time DATETIME , \n" 
        + "sender varchar , \n"
        + "recever varchar ,\n"
        + "message varchar );";
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement prepa = connection.prepareStatement(sql);)
        { prepa.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
           
            
    }

    public void addmessageData(String name1, String name2,Date time,String message){
        String sql = "INSERT INTO history(time,sender,recever,message) VALUES(?,?,?,?)";
        lock.lock();
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement prepa = connection.prepareStatement(sql)){
            prepa.setTimestamp(1, new Timestamp(time.getTime()));
            prepa.setString(2, name1);
            prepa.setString(3, name2);
            prepa.setString(4, message);
            prepa.executeUpdate();
        }catch (SQLException e) {
            System.out.println("[Model] BaseDeDonnee: addmessageData error");
            e.printStackTrace();
        }finally{lock.unlock();}
    }

      public List<dataMessage> gethistory(String name, String namefriend){
        List<dataMessage> historyList =new ArrayList<>();
        String sql="SELECT time, sender,recever, message FROM history WHERE (recever = ? AND sender = ?) OR(sender=? AND recever=?)   " ;
        lock.lock();
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement prepa = connection.prepareStatement(sql);
            ){
            prepa.setString(1,name);
            prepa.setString(2, namefriend);
            prepa.setString(3, name);
            prepa.setString(4, namefriend);
            try(  ResultSet resultSet = prepa.executeQuery()){
                while (resultSet.next()) {
                    Date time = resultSet.getTimestamp("time");
                    String sender = resultSet.getString("sender");
                    String recever = resultSet.getString("recever");
                    String message = resultSet.getString("message");
                    //System.out.println(time+"|||"+sender+"|||"+recever+"|||"+message); 
                    historyList.add(new dataMessage(time, sender, recever ,message));
                }
            }
        }catch(SQLException e) {
            System.out.println("[Model] BaseDeDonnee: error de get history");
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
        return historyList;
    }

    public static  void changerUserName(String oldName, String newName){
        String sql ="UPDATE history set sender = ? WHERE sender = ?";
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement prepa = connection.prepareStatement(sql)) {
            prepa.setString(1, newName);
            prepa.setString(2, oldName);
            prepa.executeUpdate();
        }catch(SQLException e) {
            System.out.println("[Model] BaseDeDonnee: error de changer name sender");
            e.printStackTrace();
        }
        sql ="UPDATE history set recever = ? WHERE recever = ?";
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement prepa = connection.prepareStatement(sql)) {
            prepa.setString(1, newName);
            prepa.setString(2, oldName);
            prepa.executeUpdate();
        }catch(SQLException e) {
            System.out.println("[Model] BaseDeDonnee: error de changer name recever");
            e.printStackTrace();
        }
    }

    public void get_all_history(){
        String sql ="SELECT time, sender,recever, message FROM history ";
        lock.lock();
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement prepa = connection.prepareStatement(sql)) {
            try(  ResultSet resultSet = prepa.executeQuery()){
                while (resultSet.next()) {
                    Date time = resultSet.getTimestamp("time");
                    String sender = resultSet.getString("sender");
                    String recever = resultSet.getString("recever");
                    String message = resultSet.getString("message");
                System.out.println(time+"|||"+sender+"|||"+recever+"|||"+message);
                }
            }
        }catch(SQLException e) {
            System.out.println("[Model] BaseDeDonnee: error de get all history");
            e.printStackTrace();
        }finally{
            lock.unlock();
        }

    }
}

