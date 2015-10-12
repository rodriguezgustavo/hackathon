package com.ml.hackathon.db;

import com.ml.hackathon.domain.User;

import java.sql.*;

/**
 * Created by gurodriguez
 */
public class UserDao extends BaseDao {


    public static User getUserInfo(String userName){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con=getConnection();

            st = con.prepareStatement("SELECT id,name,surname FROM user WHERE user_name=?");
            st.setString(1,userName);
            rs = st.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt(1),userName,rs.getString(2),rs.getString(3));
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
         finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return null;
    }

    public static boolean updateUser(User user){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con=getConnection();

            st = con.prepareStatement("UPDATE user SET name=?,surname=? WHERE id=?");
            st.setString(1,user.getName());
            st.setString(2,user.getSurname());
            st.setInt(3, user.getUserId());

            return st.executeUpdate()==1;

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return false;
    }
}
