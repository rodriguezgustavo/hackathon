package com.ml.hackathon.db;

import com.ml.hackathon.domain.Order;
import com.ml.hackathon.domain.Shipper;
import com.ml.hackathon.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurodriguez
 */
public class OrderDao extends BaseDao {


    public static List<Order> getOrders(){
        List<Order> result=new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con=getConnection();
            //TODO
            st = con.prepareStatement("SELECT id,order_id FROM order");
            rs = st.executeQuery();

            while (rs.next()) {
                result.add(new Order(rs.getInt(1),rs.getString(2)));
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

        return result;
    }


}
