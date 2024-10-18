package database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://cornelius.db.elephantsql.com:5432/wmofsnqu";
    private static final String USER = "wmofsnqu";
    private static final String PASSWORD = "skGjU3q6Rj-7oFMLv3scHqqSPwCTvClr";


    public Connection getConnection() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


}
