package org.unibl.etf.ip.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

public class DBUtil {

    private static final HikariDataSource dataSource;

    static {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

    	
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/etfbl_ip?useSSL=false&serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("root");
        config.setMaximumPoolSize(10); 
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000); 
        config.setConnectionTimeout(10000); 
        config.setLeakDetectionThreshold(5000); 
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");


        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
}
