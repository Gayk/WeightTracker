package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class SQLController {
    public static Connection getConnection(String dataBaseName) throws SQLException {
        String dataBaseConnection = String.format("jdbc:mysql://localhost:3306/%s?seUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", dataBaseName);
        return DriverManager.getConnection(dataBaseConnection, "root", "kochamjave");
    }


    public static ResultSet executeQuery(Connection con, String sql, String... conditions) throws SQLException {
        PreparedStatement stm = con.prepareStatement(sql);
        for (int i = 1; i <= conditions.length; i++) {
            stm.setString(i, conditions[i - 1]);
        }
        return stm.executeQuery();
    }

    public static void executeUpdate(Connection con, String sql, String... conditions) throws SQLException {
        PreparedStatement stm = con.prepareStatement(sql);
        for (int i = 1; i <= conditions.length; i++) {
            stm.setString(i, conditions[i - 1]);
        }
        stm.executeUpdate();
    }

    public static void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        while (rs.next()) {
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                System.out.print(String.format("%s: %s, ", meta.getColumnName(i), rs.getString(i)));
            }
            System.out.println();
        }
    }
}