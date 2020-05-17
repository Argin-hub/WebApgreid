package my.library.jdbc;

import my.library.jdbc.exception.ConnectionException;
import my.library.jdbc.exception.PropertiesException;
import my.library.jdbc.exception.ResourcesException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {

    private String user;
    private String password;
    private String driver;
    private String url;
    private int poolSize;
    private int timeOut;
    private ResourcesQueue<Connection> connections = null;

    private static ConnectionPool connectionPool;

    private ConnectionPool() {
        try {
            init();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    private void init() throws ConnectionException {
        try {
            loadProperties();
            connections = new ResourcesQueue<Connection>(poolSize, timeOut);

            while (connections.size() < poolSize) {
                try {
                    Class.forName(driver);
                } catch (ClassNotFoundException e) {
                   throw  new ConnectionException("Cant find driver for JDBC MySql" , e);
                }
                Connection connection = DriverManager.getConnection(url, user, password);
                connections.addResource(connection);
            }
        } catch (SQLException | PropertiesException e) {
            e.printStackTrace();
        }
    }

    private void loadProperties() throws PropertiesException {
        Properties properties = new Properties();
        try {
            properties.load(ConnectionPool.class.getResourceAsStream("/db.properties"));
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            poolSize = Integer.parseInt(properties.getProperty("pool_size"));
            timeOut = Integer.parseInt(properties.getProperty("timeout"));
            driver = properties.getProperty("driver");
        } catch (IOException e) {
            throw new PropertiesException("Not found properties file with connecting settings", e);
        }
    }

    public Connection getConnection() throws ResourcesException {
        return connections.takeResource();
    }

    public void returnConnection(Connection connection) {
        connections.returnResource(connection);
    }
}
