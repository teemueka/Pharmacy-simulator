package simu.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The MariaDbConnection class provides methods to establish and manage connections to the MariaDB database.
 */
public class MariaDbConnection {

    /**
     * Static connection object to hold the database connection.
     * <p>
     * This variable stores the database connection established by the getConnection() method.
     * It is static to ensure that only one connection is maintained throughout the application.
     * </p>
     */
    private static Connection conn = null;

    /**
     * Retrieves a connection to the MariaDB database.
     *
     * @return A connection to the MariaDB database.
     */
    public static Connection getConnection() {
        if (conn==null) {
            try {
                conn = DriverManager.getConnection(
                        "jdbc:mariadb://localhost:3306/g6_simudb?user=appuser&password=password");
            } catch (SQLException e) {
                System.out.println("Connection failed.");
                e.printStackTrace();
            }
            return conn;

        }
        else {
            return conn;
        }
    }

    /**
     * Closes the connection to the MariaDB database.
     */
    public static void terminate() {
        try {
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
