package com.example.myapplication2.DBClass;

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
    public static DBGameService dbGameService=null;

    /**
     * 构造方法
     */
    private DBGameService(){

    }

    /**
     * 获取数据库对象
     */
    public static DBGameService getDbGameService(){
        if (dbGameService == null){
            dbGameService = new DBGameService();
        }
        return dbGameService;
    }

    /**
     * 获取游戏评论信息
     */
//    public String[][] getGameComment(){
//        String[][] gamecomment = new String[100][3];
//        String sql = "select username,gamemark,gamecomment from usergamemark where gamename=?";
//        con= DBUtil.getSQLConnection();
//        try {
//            if(con!=null&&(!con.isClosed())){
//                ps= (PreparedStatement) con.prepareStatement(sql);
//                if(ps!=null){
//                    rs= ps.executeQuery();
//                    if(rs!=null){
//                        int i = 0;
//                        while(rs.next()){
//                            User user=new User(null, null);
//                            user.setUser_Name(rs.getString("username"));
//                            user.setPassword(rs.getString("password"));
//                            list.add(user);
//                        }
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        DBUtil.closeAll(con,ps,rs);//关闭相关操作
//        return gamecomment;
//    }

}
