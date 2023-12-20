package model;

import java.sql.*;
import java.util.*;
import java.util.Date;


public class BaseDeDonnee {
    public static String url = "jdbc:sqlite:BaseDeDonnee/java.db";
    public static record dataMessage(Date time, String sender, String message){}
    public BaseDeDonnee(){
        create_new_basededonne();
    }


    public void create_new_basededonne(){
         
            DatabaseMetaData meta;
            try {
                Connection connection=DriverManager.getConnection(url);
                meta =connection.getMetaData();
                System.out.println("[Model] nom de driver"+meta.getDriverName());
                System.out.println("[Model] creation base de donnee reussir");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            
        
    }
    
    public boolean tableExiste(String table_name){
        boolean exist = false;
        try {
            Connection connection=DriverManager.getConnection(url);
            ResultSet resultSet= connection.getMetaData().getTables(null, null, table_name, null);
           exist=resultSet.next();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         return exist;
    }
    public String create_new_table(String ip1, String ip2){
       
        String name;
        if (tableExiste(ip1+ip2)){name=ip1+ip2;}
        else if (tableExiste(ip2+ip1)){name =ip2+ip1;}
        else {
            
            name =ip1+ip2;
            String sql = "CREAT TABLE IF NOT EXISTS " +name+ "(\n"
            + "time DATETIME primary key, \n" 
            + "sender varchar \n"
            + "recever varchar \n"
            + "message varchar );";
            try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement prepa = connection.prepareStatement(sql);)
            { prepa.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
           
            
        }
        return name;
    }

    public void addmessageData(String name , String ip1, String ip2,Date time,String message){
        String sql = "INSERT INTO " +name +"(time,sender,recever,message) VALUE(?,?,?,?)";
       
            try(Connection connection = DriverManager.getConnection(url);
                PreparedStatement prepa = connection.prepareStatement(sql)){
                    prepa.setTimestamp(1, new Timestamp(time.getTime()));
                    prepa.setString(2, ip1);
                    prepa.setString(3, ip2);
                    prepa.setString(4, message);
                    prepa.executeUpdate();
                }catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public List<dataMessage> gethistory(String name){
        List<dataMessage> historyList =new ArrayList<>();
        String sql="SELECT time, sender, message FROM "+name ;
            try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement prepa = connection.prepareStatement(sql);)
            { ResultSet resultSet = prepa.executeQuery();
                while (resultSet.next()) {
                    Date time = resultSet.getTimestamp("time");
                    String sender = resultSet.getString("sender");
                    String message = resultSet.getString("message");
                    historyList.add(new dataMessage(time, sender, message));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return historyList;
    }
}

