package model;

import java.sql.*;
import java.util.*;
import java.util.Date;


public class BaseDeDonnee {
    public static String url = "jdbc:sqlite:bdd.db";
    public static record dataMessage(Date time, String sender, String recever, String message){}
    public BaseDeDonnee(){
        create_new_basededonne();
        create_new_table();
    }


    public void create_new_basededonne(){
         
            DatabaseMetaData meta;
            try {
                Connection connection=DriverManager.getConnection(url);
                meta =connection.getMetaData();
                System.out.println("[Model] nom de driver: "+meta.getDriverName());
                System.out.println("[Model] creation base de donnee reussir");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            
        
    }
    
    // public boolean tableExiste(String table_name){
    //     boolean exist = false;
    //     try {
    //         Connection connection=DriverManager.getConnection(url);
    //         ResultSet resultSet= connection.getMetaData().getTables(null, null, table_name, null);
    //        exist=resultSet.next();
    //     } catch (SQLException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }
    //      return exist;
    // }
    public void create_new_table(){
            String sql = "CREATE TABLE IF NOT EXISTS history(\n"
            + "time DATETIME , \n" 
            + "sender varchar , \n"
            + "recever varchar ,\n"
            + "message varchar );";
            try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement prepa = connection.prepareStatement(sql);)
            { prepa.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
           
            
        }

    public void addmessageData(String name1, String name2,Date time,String message){
        String sql = "INSERT INTO history(time,sender,recever,message) VALUES(?,?,?,?)";
        
            try(Connection connection = DriverManager.getConnection(url);
                PreparedStatement prepa = connection.prepareStatement(sql)){
                    prepa.setTimestamp(1, new Timestamp(time.getTime()));
                    prepa.setString(2, name1);
                    prepa.setString(3, name2);
                    prepa.setString(4, message);
                    prepa.executeUpdate();
                }catch (SQLException e) {
                 System.out.println("Model] addmessageData error");
                    e.printStackTrace();
            }
       
    }

    public List<dataMessage> gethistory(){
        List<dataMessage> historyList =new ArrayList<>();
        String sql="SELECT time, sender,recever, message FROM history" ;
            try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement prepa = connection.prepareStatement(sql);)
            { ResultSet resultSet = prepa.executeQuery();
                while (resultSet.next()) {
                    Date time = resultSet.getTimestamp("time");
                    String sender = resultSet.getString("sender");
                    String recever = resultSet.getString("recever");
                    String message = resultSet.getString("message");
                   
                    historyList.add(new dataMessage(time, sender, recever ,message));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return historyList;
    }

    public static void changerUserName(String oldName, String newName){
        String sql ="UPDATE history set sender = ? WHERE sender = ?";
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement prepa = connection.prepareStatement(sql)) 
        {
            prepa.setString(1, newName);
            prepa.setString(2, oldName);
            prepa.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("[Model] [BDD] error de changer name sender");
        }
        sql ="UPDATE history set recever = ? WHERE recever = ?";
        try (Connection connection = DriverManager.getConnection(url);
            PreparedStatement prepa = connection.prepareStatement(sql)) 
        {
            prepa.setString(1, newName);
            prepa.setString(2, oldName);
            prepa.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("[Model] [BDD] error de changer name recever");
        }
         
    }

}

