package by.epam.project.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Enumeration;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The type Connection pool.
 */
public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final ConnectionPool instance = new ConnectionPool();

    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> busyConnections;

    private static final int DEFAULT_POOL_SIZE = 8;

    private ConnectionPool() {
        DatabaseConfig databaseConfig = DatabaseConfig.getInstance();

        freeConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        busyConnections = new ArrayDeque<>();
        try {
            String url = databaseConfig.getUrl();
            String username = databaseConfig.getUsername();
            String password = databaseConfig.getPassword();
            String sqlDriver = databaseConfig.getDriverName();
            Class.forName(sqlDriver);
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(url, username, password);
                freeConnections.add(new ProxyConnection(connection));
            }
        } catch (ClassNotFoundException | SQLException exp) {
            logger.fatal("Connection pool is not initialize", exp);
            throw new RuntimeException("Connection pool is not initialize", exp);
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ConnectionPool getInstance() {
        return instance;
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = freeConnections.take();
            busyConnections.add(proxyConnection);
        } catch (InterruptedException exp) {
            logger.error("The connection is not received", exp);
        }
        return proxyConnection;
    }

    /**
     * Release connection.
     *
     * @param connection the connection
     */
    public void releaseConnection(Connection connection) {
        if (connection.getClass() == ProxyConnection.class
                && busyConnections.remove(connection)) {
            freeConnections.offer((ProxyConnection) connection);
        } else {
            logger.error("Invalid connection type passed");
        }
    }

    /**
     * Destroy pool.
     */
    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (SQLException | InterruptedException exp) {
                logger.error("The pool was not destroyed", exp);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException exp) {
                logger.error("Error while deregister drivers", exp);
            }
        }
    }
}
