package Database;

import Database.ConnectDB;
import Models.UserInfo;
import com.sun.jdi.connect.spi.Connection;
import java.beans.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import managementproject.data;

public class UserDAO {

    static ConnectDB connect = new ConnectDB();
    static java.sql.Connection cn = null;
    static java.sql.Statement stm = null;
    static ResultSet rs = null;// hung ket qua tra ve
    static Scanner sc;// gia btri la null -> tao doi tuong
    static PreparedStatement pStm = null;

    ArrayList<UserInfo> userlist = new ArrayList<>();

    public ArrayList<UserInfo> listDB() {

        String sql = "select * from UserInfo";
        try {
            cn = connect.GetConnectDB();
            stm = cn.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                UserInfo b = new UserInfo();
                b.setUserId(rs.getString(1));
                b.setUserName(rs.getString(2));
                b.setUserPassWord(rs.getString(3));
                b.setUserPosition(rs.getString(4));
                b.setUserDate(rs.getDate(5));
                userlist.add(b);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                cn.close();
                stm.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return userlist;
    }

    public UserInfo addDB(UserInfo u) {

        String checksql = "SELECT userId FROM UserInfo WHERE userId =?";
        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(checksql);
            pStm.setString(1, u.getUserId());
            rs = pStm.executeQuery();

            if (rs.next()) {
                return null;
            } else {
                String sql = "insert into UserInfo(userId, userName,userPassWord,userPosition,question,answer,userDate) "
                        + "VALUES (?, ?, ?, ?, ?,?,?)";

                pStm = cn.prepareStatement(sql);
                pStm.setString(1, u.getUserId());
                pStm.setString(2, u.getUserName());
                pStm.setString(3, u.getUserPassWord());
                pStm.setString(4, u.getUserPosition());
                pStm.setString(5, u.getQuestion());
                pStm.setString(6, u.getAnswer());

                // Convert java.util.Date to java.sql.Date
                java.sql.Date sqlDate = new java.sql.Date(u.getUserDate().getTime());
                pStm.setDate(7, sqlDate);
                pStm.executeUpdate();
                return u;
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                cn.close();
                pStm.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return null;
    }

    public void deleteDB(String id) {
        String sql = "delete from UserInfo where userId=? ";
        try {
            cn = connect.GetConnectDB();//mo ket noi
            pStm = cn.prepareStatement(sql);
            pStm.setString(1, id); //gan du lieu        
            pStm.execute();//chay cau lenh sql    

        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                cn.close();
                pStm.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public void editDB(UserInfo u) {
        String sql = "update UserInfo  set userPassWord=? where userName=? ";
        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(sql);
            pStm.setString(1, u.getUserPassWord());
            pStm.setString(2, u.getUserName());
            pStm.execute();
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                cn.close();
                pStm.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public UserInfo searchDB(UserInfo u) {
        String sql = "select * from UserInfo"
                + " WHERE userName=? and userPassWord=? ";
        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(sql);
            pStm.setString(1, u.getUserName());
            pStm.setString(2, u.getUserPassWord());
            rs = pStm.executeQuery();

            if (rs.next()) {
                return u;
            } else {
                return null;
                

            }
        } catch (Exception e) {
            e.getMessage();
            return null;
        } finally {
            try {
                cn.close();
                pStm.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
        
    }

    public UserInfo searchUser(UserInfo u) {
        String sql = "select * from UserInfo"
                + " WHERE userName=? and question=? and  answer=? ";
        try {
            cn = connect.GetConnectDB();
            pStm = cn.prepareStatement(sql);
            pStm.setString(1, u.getUserName());
            pStm.setString(2, u.getQuestion());
            pStm.setString(3, u.getAnswer());

            rs = pStm.executeQuery();

            if (rs.next()) {
                return u;
            } else {
                return null;

            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                cn.close();
                pStm.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return u;
    }
}
