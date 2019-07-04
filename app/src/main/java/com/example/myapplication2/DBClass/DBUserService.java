package com.example.myapplication2.DBClass;

import com.example.myapplication2.User.User;
import com.mysql.jdbc.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DBUserService {

    private Connection con=null; //打开数据库对象
    private PreparedStatement ps=null;//操作整合sql语句的对象
    private ResultSet rs=null;//查询结果的集合

    //DBService 对象
    public static DBUserService dbUserService;

    /**
     * 构造方法
     */
    private DBUserService(){

    }

    /**
     * 获取数据库对象
     */
    public static DBUserService getDbUserService(){
        dbUserService = new DBUserService();
        return dbUserService;
    }

    /**
     * 查询用户
     */
    public User getUserByName(String username){
        //结果存放
        User user = new User(username, null, null, null,
                null, null, null);
        //MySQL 语句
        String sql="select usernote,userbirth,usersex,usercity,userphone from user where username=?";
        //获取链接数据库对象
        con= DBUtil.getSQLConnection();
        try {
            if(con!=null&&(!con.isClosed())){
                ps= (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1,username);
                if(ps!=null){
                    rs= ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()){
                            user.setUsernote(rs.getString("usernote"));
                            user.setUserbirth(rs.getString("userbirth"));
                            user.setUsersex(rs.getString("usersex"));
                            user.setUsercity(rs.getString("usercity"));
                            user.setUserphone(rs.getString("userphone"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtil.closeAll(con,ps,rs);//关闭相关操作
        return user;
    }

    /**
     * 注册
     */
    public int userInsert(User user){
        int result = -1;

        con = DBUtil.getSQLConnection();

        String sql="INSERT INTO user (username,password) VALUES (?,?)";
        try {
            boolean closed=con.isClosed();
            if((con!=null)&&(!closed)){
                ps= (PreparedStatement) con.prepareStatement(sql);
                String name = user.getUser_Name();
                String password = user.getPassword();
                ps.setString(1,name);//第一个参数 name 规则同上
                ps.setString(2,password);//第二个参数 phone 规则同上
                result=ps.executeUpdate();//返回1 执行成功
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtil.closeAll(con, ps);
        return result;
    }

    /**
     * 登录密码验证
     */
    public String findUser(String username){
        String sql="select password from user where username=?";
        String password = null;
        //获取链接数据库对象
        con= DBUtil.getSQLConnection();
        try {
            if (con != null && (!con.isClosed())) {
                ps = (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1, username);
                if (ps != null) {
                    rs= ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()) {
                            password = rs.getString("password");
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        DBUtil.closeAll(con,ps,rs);//关闭相关操作
        return password;
    }

    /**
     * 修改用户密码
     */
    public int changePassword(String name, String password){
        int result=-1;
        con= DBUtil.getSQLConnection();
        //MySQL 语句
        String sql="update user set password=? where username=?";
        try {
            boolean closed=con.isClosed();
            if(con!=null&&(!closed)){
                ps= (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1,password);
                ps.setString(2,name);
                result=ps.executeUpdate();//返回1 执行成功
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtil.closeAll(con,ps);//关闭相关操作
        return result;
    }

    /**
     * 密保查询
     */
    public String findUserProtect(String username){
        String sql="select usersecret from user where username=?";
        String userprotect = null;
        //获取链接数据库对象
        con= DBUtil.getSQLConnection();
        try {
            if (con != null && (!con.isClosed())) {
                ps = (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1, username);
                if (ps != null) {
                    rs= ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()) {
                            userprotect = rs.getString("usersecret");
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        DBUtil.closeAll(con,ps,rs);//关闭相关操作
        return userprotect;
    }

    /**
     * 加入密保信息
     */
    public int changeProtect(String name, String userprotect){
        int result=-1;
        con= DBUtil.getSQLConnection();
        //MySQL 语句
        String sql="update user set usersecret=? where username=?";
        try {
            boolean closed=con.isClosed();
            if(con!=null&&(!closed)){
                ps= (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1,userprotect);
                ps.setString(2,name);
                result=ps.executeUpdate();//返回1 执行成功
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtil.closeAll(con,ps);//关闭相关操作
        return result;
    }

    /**
     * 更新用户信息
     */
    public int changeUserinfo(User user){
        int result=-1;
        con= DBUtil.getSQLConnection();
        //MySQL 语句
        String sql="update user set usernote=?,userbirth=?,usersex=?,usercity=?,userphone=? where username=?";
        try {
            boolean closed=con.isClosed();
            if(con!=null&&(!closed)){
                ps= (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1,user.getUsernote());
                ps.setString(2,user.getUserbirth());
                ps.setString(3,user.getUsersex());
                ps.setString(4,user.getUsercity());
                ps.setString(5,user.getUserphone());
                ps.setString(6,user.user_Name);
                result=ps.executeUpdate();//返回1 执行成功
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtil.closeAll(con,ps);//关闭相关操作
        return result;
    }


    /**
     * 关注游戏状态查询（0和2没有关注，2有记录，1表示已关注）
     */
    public int findGameState(String name, String gamename){
        String selectSql = "select gamestate from usergame where username=? and gamename=?";
        con = DBUtil.getSQLConnection();
        int gamestate = 0;
        try {
            boolean closed=con.isClosed();
            if((con!=null)&&(!closed)){
                ps= (PreparedStatement) con.prepareStatement(selectSql);
                ps.setString(1,name);//第一个参数 name 规则同上
                ps.setString(2,gamename);
                if (ps != null) {
                    rs= ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()) {
                            gamestate = rs.getInt("gamestate");
                            if(gamestate == 0){
                                DBUtil.closeAll(con, ps, rs);
                                return 2;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtil.closeAll(con, ps, rs);
        return gamestate;
    }

    /**
     * 关注游戏按钮点击
     */
    public int focusGame(String username, String gamename, int gamestate){
        String updateSql = "update usergame set gamestate=? where username=? and gamename=?";
        String insertSql = "insert into usergame(username, gamename, gamestate) values(?, ?, ?)";
        con = DBUtil.getSQLConnection();
        try {
            boolean closed = con.isClosed();
            if((con!=null)&&(!closed)){
                if (gamestate == 0){
                    ps= (PreparedStatement) con.prepareStatement(insertSql);
                    ps.setString(1,username);
                    ps.setString(2,gamename);
                    ps.setInt(3,1);
                    ps.executeUpdate();
                    DBUtil.closeAll(con, ps);
                    return 1;
                }
                if (gamestate == 1){
                    ps= (PreparedStatement) con.prepareStatement(updateSql);
                    ps.setInt(1,0);
                    ps.setString(2,username);
                    ps.setString(3,gamename);
                    ps.executeUpdate();
                    DBUtil.closeAll(con, ps);
                    return 1;
                }
                if (gamestate == 2){
                    ps= (PreparedStatement) con.prepareStatement(updateSql);
                    ps.setInt(1,1);
                    ps.setString(2,username);
                    ps.setString(3,gamename);
                    ps.executeUpdate();
                    DBUtil.closeAll(con, ps);
                    return 1;
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取用户关注的游戏
     */
    public String[] findUserGame(String username){
        String sql = "select gamename from usergame where username=? and gamestate=1";
        String[] games = new String[100];//返还用户关注游戏ID的列表
        con= DBUtil.getSQLConnection();//获取链接数据库对象
        try {
            if (con != null && (!con.isClosed())) {
                ps = (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1, username);
                if (ps != null) {
                    rs= ps.executeQuery();
                    int i=0;
                    if(rs!=null){
                        while(rs.next()) {
                            games[i] = rs.getString("gamename");
                            i++;
                        }
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        DBUtil.closeAll(con,ps,rs);//关闭相关操作
        return games;
    }


//    /**
//     * * 删除操作
//     */
//    public int delUserData(String password){
//        int result=-1;
//        if(!StringUtils.isEmptyOrWhitespaceOnly(password)){
//            //获取链接数据库对象
//            con= DBUtil.getSQLConnection();
//            //MySQL 语句
//            String sql="delete from user where password=?";
//            try {
//                boolean closed=con.isClosed();
//                if((con!=null)&&(!closed)){
//                    ps= (PreparedStatement) con.prepareStatement(sql);
//                    ps.setString(1, password);
//                    result=ps.executeUpdate();//返回1 执行成功
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        DBUtil.closeAll(con,ps);//关闭相关操作
//        return result;
//    }
}
