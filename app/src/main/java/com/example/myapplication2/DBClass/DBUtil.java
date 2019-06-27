package com.example.myapplication2.DBClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

    /**
     * 连接数据库
     */
//    public static Connection getSQLConnection(String ip, String user, String pwd, String db)
    public static Connection getSQLConnection()
    {
        Connection con = null;
        try
        {
            System.out.println(44);

            //加载JDBC驱
            Class.forName("com.mysql.jdbc.Driver");

            //连接
            con = DriverManager.getConnection("jdbc:mysql://192.168.43.211:3306/populus", "populus", "populus");
            //con = DriverManager.getConnection("jdbc:mysql://" + ip + ":3306/" + db, user, pwd);

        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * 关闭数据库
     * @param con
     * @param ps
     */
    public static void closeAll(Connection con, PreparedStatement ps){
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 关闭数据库
     * @param conn
     * @param ps
     * @param rs
     */
    public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
