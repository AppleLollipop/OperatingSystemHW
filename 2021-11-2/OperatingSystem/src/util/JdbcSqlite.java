package util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author xzy
 * @create 2021/10/31 16:48
 */
public class JdbcSqlite {
    private final String url = "jdbc:sqlite:database.db";
    private final String jdbc = "org.sqlite.JDBC";

    public Connection getJDBC(){
        Connection connection = null;

        try {
            Class.forName(jdbc);
            connection = DriverManager.getConnection(url);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void main(String[] args) {
        System.out.println(new JdbcSqlite().getJDBC());
    }
}
