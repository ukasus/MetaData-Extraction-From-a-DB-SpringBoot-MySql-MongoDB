package com.ujjawal.metadataextractor.DatabaseToBeExtracted;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Component
public class JDBCConnector {

    private Connection connection;

    @Value("${DbFromMetaExtract_jdbc_url}")
    String url ;
    @Value("${DbFromMetaExtract_jdbc_username}")
    String username;
    @Value("${DbFromMetaExtract_jdbc_password}")
    String password;

    //Connection provider to the database
    public Connection getConnection(){

        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return connection;
    }


    //Execute query on db(MYsql)
    public boolean executeQueryToOldDb(String query)
    {

        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement stmt = connection.createStatement();

            stmt.execute(query);
            return true;

        }
        catch(Exception e)
        {
            e.printStackTrace();

            return false;

        }
    }


}
