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
    public static DBUserService dbUserService=null;

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
     * 查询
     */
    public List<User> getUserData(){
        //结果存放集合
        List<User> list=new ArrayList<User>();
        //MySQL 语句
        String sql="select * from user";
        //获取链接数据库对象
        con= DBUtil.getSQLConnection();
        try {
            if(con!=null&&(!con.isClosed())){
                ps= (PreparedStatement) con.prepareStatement(sql);
                if(ps!=null){
                    rs= ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()){
                            User user=new User(null, null);
                            user.setUser_Name(rs.getString("username"));
                            user.setPassword(rs.getString("password"));
                            list.add(user);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBUtil.closeAll(con,ps,rs);//关闭相关操作
        return list;
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
     * 删除操作
     */
    public int delUserData(String password){
        int result=-1;
        if(!StringUtils.isEmptyOrWhitespaceOnly(password)){
            //获取链接数据库对象
            con= DBUtil.getSQLConnection();
            //MySQL 语句
            String sql="delete from user where password=?";
            try {
                boolean closed=con.isClosed();
                if((con!=null)&&(!closed)){
                    ps= (PreparedStatement) con.prepareStatement(sql);
                    ps.setString(1, password);
                    result=ps.executeUpdate();//返回1 执行成功
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBUtil.closeAll(con,ps);//关闭相关操作
        return result;
    }

    /**
     * 插入操作（注册）
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
     * 更新操作（更改密码）
     */
    public int updateUserData(String name, String password){
        int result=-1;
        if(!StringUtils.isEmptyOrWhitespaceOnly(password)){
            //获取链接数据库对象
            con= DBUtil.getSQLConnection();
            //MySQL 语句
            String sql="update user set password=? where username=?";
            try {
                boolean closed=con.isClosed();
                if(con!=null&&(!closed)){
                    ps= (PreparedStatement) con.prepareStatement(sql);
                    ps.setString(1,password);//第一个参数state 一定要和上面SQL语句字段顺序一致
                    ps.setString(2,name);//第二个参数 phone 一定要和上面SQL语句字段顺序一致
                    result=ps.executeUpdate();//返回1 执行成功
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        String updateSql = "update usergame set gamestate=? where username=?";
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
                    ps.executeUpdate();
                    DBUtil.closeAll(con, ps);
                    return 1;
                }
                if (gamestate == 2){
                    ps= (PreparedStatement) con.prepareStatement(updateSql);
                    ps.setInt(1,1);
                    ps.setString(2,username);
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
    public List<Integer> findUserGame(String username){
        String sql = "select gameid from usergame where username=? and gamestate=1";
        List<Integer> games = new ArrayList<>();//返还用户关注游戏ID的列表
        con= DBUtil.getSQLConnection();//获取链接数据库对象
        try {
            if (con != null && (!con.isClosed())) {
                ps = (PreparedStatement) con.prepareStatement(sql);
                ps.setString(1, username);
                if (ps != null) {
                    rs= ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()) {
                            int gameid = rs.getInt("gameid");
                            games.add(gameid);
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
}
