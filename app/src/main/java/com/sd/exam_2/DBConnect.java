package com.sd.exam_2;

import java.sql.*;

public class DBConnect {
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String user = "ket";
            String pwd = "1234";
            String url = "jdbc:mysql://192.168.0.107/db1?&characterEncoding=UTF-8";

            return DriverManager.getConnection(url,user,pwd);
        }catch (Exception ex){
            return null;
        }
    }
}
