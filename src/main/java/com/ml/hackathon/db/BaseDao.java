package com.ml.hackathon.db;

import com.ml.hackathon.util.PropertyReader;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by gurodriguez
 */
public class BaseDao {
    
    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        String dbHost=(String) PropertyReader.getProperty("db.host");
        int dbPort=Integer.parseInt((String)PropertyReader.getProperty("db.port"));
        String dbName=(String)PropertyReader.getProperty("db.name");
        String dbUrl = String.format("jdbc:mysql://%1$s:%2$s/%3$s",dbHost,dbPort,dbName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername((String)PropertyReader.getProperty("db.user"));
        dataSource.setPassword((String)PropertyReader.getProperty("db.pass"));
    }

    protected static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}