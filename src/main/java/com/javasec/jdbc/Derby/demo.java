package com.javasec.jdbc.Derby;

import java.sql.DriverManager;

public class demo {
    public static void main(String[] args) throws Exception{
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        //DriverManager.getConnection("jdbc:derby:dbname;create=true");
        DriverManager.getConnection("jdbc:derby:dbname;startMaster=true;slaveHost=127.0.0.1");
    }
}
