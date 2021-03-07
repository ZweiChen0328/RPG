package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class Connect {

    private static Connection conn = null;

    public static void createConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:src/database/game.db");
        } catch (ClassNotFoundException | SQLException error) {
            error.printStackTrace();
        }
    }

    public static ObservableList getAllAchievement() throws SQLException {
        Statement stmt = conn.createStatement();
        ObservableList<String> achievementList = FXCollections.<String>observableArrayList();
        String sql = "select * from achievement where accomplish = 1";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            achievementList.addAll(result.getString(1), result.getString(2));
        }
        String sql2 = "select * from achievement where accomplish = 0";
        ResultSet result2 = stmt.executeQuery(sql2);
        while (result2.next()) {
            achievementList.addAll(result.getString(1), "???????????????????");
        }
        return achievementList;
    }

    public int getAchCount() throws SQLException {
        int count = 0;
        Statement stmt = conn.createStatement();
        try {
            String sql = "select count(*) from achievement";
            ResultSet r = stmt.executeQuery(sql);
            r.next();
            count = r.getInt(1);
        } catch (java.sql.SQLException a) {
        }
        return count;
    }

    public int accomplishAch(String name) throws SQLException {
        int bool = 0;
        Statement stmt = conn.createStatement();
        String sql = "select * from achievement where name = '" + name + "' and accomplish = '" + 0 + "'";
        String sql2 = "update achievement set accomplish = '1' where name = '" + name + "'";
        System.out.println(sql);
        System.out.println(sql2);
        ResultSet r = stmt.executeQuery(sql);
        if (r.next()) {//判斷是否已達成過
            bool = 1;
            System.out.println(name + "成就之前未達成，現已修改為達成");
            stmt.executeUpdate(sql2);
        } else {//代表成就之前達成過，不需要進行update
            bool = 0;
            System.out.println(name + "成就之前就達成");
        }
        return bool;
    }

}
