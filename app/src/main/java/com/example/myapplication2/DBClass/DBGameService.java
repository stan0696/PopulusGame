package com.example.myapplication2.DBClass;

import com.example.myapplication2.User.User;
import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBGameService {
    private Connection con=null; //打开数据库对象
    private PreparedStatement ps=null;//操作整合sql语句的对象
    private ResultSet rs=null;//查询结果的集合

    //DBService 对象
    public static DBGameService dbgameservice=null;

    /**
     * 构造方法
     */
    public DBGameService(){

    }

    /**
     * 获取数据库对象
     */
    public static DBGameService getDbGameService(){
        if (dbgameservice == null){
            dbgameservice = new DBGameService();
        }
        return dbgameservice;
    }

    /**
     * 查询下载量
     */
    public int getGameDL(String gamename){
        String sql="select download from gameDL where gamename=?";
        int gamedl = 0;
        //获取链接数据库对象
        con= DBUtil.getSQLConnection();
        try {
            if (con != null && (!con.isClosed())) {
                ps = (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1, gamename);
                if (ps != null) {
                    rs= ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()) {
                            gamedl = rs.getInt("download");
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        DBUtil.closeAll(con,ps,rs);//关闭相关操作
        return gamedl;
    }

    /**
     * 更新游戏下载量
     */
    public void updateGameDL(String gamename, int gameDLnum){
        String sql="update gameDL set download=? where gamename=?";
        //获取链接数据库对象
        con= DBUtil.getSQLConnection();
        try {
            if (con != null && (!con.isClosed())) {
                ps = (PreparedStatement) con.prepareStatement(sql);
                ps.setInt(1, gameDLnum);
                ps.setString(2, gamename);
                ps.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        DBUtil.closeAll(con,ps);//关闭相关操作
    }

    /**
     * 获取游戏评论
     */
    public String[][] getGameComment(String gamename){
        String[][] gamecomment = new String[100][3];
        String sql = "select username,gamemark,gamecomment from usergamemark where gamename=?";
        //获取链接数据库对象
        con= DBUtil.getSQLConnection();
        try {
            if (con != null && (!con.isClosed())) {
                ps = (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1, gamename);
                if (ps != null) {
                    int i = 0;
                    rs= ps.executeQuery();
                    if(!rs.isLast()){
                        while(rs.next()) {
                            gamecomment[i][0] = rs.getString("username");
                            gamecomment[i][1] = rs.getString("gamemark");
                            gamecomment[i][2] = rs.getString("gamecomment");
                            i++;
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        DBUtil.closeAll(con,ps,rs);//关闭相关操作
        return gamecomment;
    }

    /**
     * 游戏平均评分
     */
    public float getAvgMark(String gamename){
        float gamemark = 0;
        String sql = "select avg(gamemark) from usergamemark where gamename=?";
        con = DBUtil.getSQLConnection();
        try{
            if(con != null && (!con.isClosed())){
                ps = (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1,gamename);
                if (ps != null){
                    rs = ps.executeQuery();
                    if(rs != null){
                        while (rs.next()){
                            gamemark = rs.getFloat("avg(gamemark)");
                        }
                    }
                }
            }
        }catch (SQLException e){
            System.out.println("cnm数据库1");
            e.getStackTrace();
        }
        DBUtil.closeAll(con, ps, rs);
        return gamemark;
    }


    /**
     * 用户评论游戏
     */
    public void setGameComment(String username, String gamename, Float gamemark, String gamecomment, int commentstate){
        String updateSql = "update usergamemark set gamemark=? , gamecomment=? where username=? and gamename=?";
        String insertSql = "insert into usergamemark(username, gamename, gamemark, gamecomment) values(?, ?, ?, ?)";
        con = DBUtil.getSQLConnection();
        try {
            boolean closed = con.isClosed();
            if((con!=null)&&(!closed)){
                if (commentstate == 0){
                    ps= (PreparedStatement) con.prepareStatement(insertSql);
                    ps.setString(1,username);
                    ps.setString(2,gamename);
                    ps.setFloat(3,gamemark);
                    ps.setString(4,gamecomment);
                    ps.executeUpdate();
                    DBUtil.closeAll(con, ps);
                }
                if (commentstate == 1){
                    ps= (PreparedStatement) con.prepareStatement(updateSql);
                    ps.setFloat(1,gamemark);
                    ps.setString(2,gamecomment);
                    ps.setString(3,username);
                    ps.setString(4,gamename);
                    ps.executeUpdate();
                    DBUtil.closeAll(con, ps);
                }
//                if (gamestate == 2){
//                    ps= (PreparedStatement) con.prepareStatement(updateSql);
//                    ps.setInt(1,1);
//                    ps.setString(2,username);
//                    ps.executeUpdate();
//                    DBUtil.closeAll(con, ps);
//                    return 1;
//                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
//        return 0;
    }
}
