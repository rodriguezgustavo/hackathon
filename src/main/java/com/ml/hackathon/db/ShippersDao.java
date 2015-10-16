package com.ml.hackathon.db;

import com.ml.hackathon.domain.Shipper;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gurodriguez
 */
public class ShippersDao extends BaseDao{


    public static Shipper addShipper(Shipper shipper){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con=getConnection();
            st = con.prepareStatement("INSERT INTO shipper (name,active,shipper_type,vehicle,email,reputation,last_latitude,last_longitude,token, last_seen) VALUES (?,?,?,?,?,?,?,?,?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, shipper.getName());
            st.setBoolean(2, shipper.isActive());
            st.setString(3, shipper.getShipperType());
            st.setString(4, shipper.getVehicle());
            st.setString(5, shipper.getEmail());
            st.setInt(6, shipper.getReputation());
            st.setDouble(7, shipper.getLatitude());
            st.setDouble(8,shipper.getLongitude());
            st.setString(9, shipper.getToken());
            st.setTimestamp(10, new Timestamp(shipper.getLastSeen().getTime()));

            if(st.executeUpdate()==1){
                ResultSet key = st.getGeneratedKeys();
                if(key.next()){
                    shipper.setId(key.getInt(1));
                    return shipper;
                }
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

    public static boolean updateShipper(Shipper shipper){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con=getConnection();
            st = con.prepareStatement("UPDATE shipper SET name=?,active=?,shipper_type=?, vehicle=?,email=?, reputation=?, last_latitude=?, last_longitude=?, token=?, last_seen=? WHERE id=?");
            st.setString(1, shipper.getName());
            st.setBoolean(2, shipper.isActive());
            st.setString(3, shipper.getShipperType());
            st.setString(4, shipper.getVehicle());
            st.setString(5, shipper.getEmail());
            st.setInt(6, shipper.getReputation());
            st.setDouble(7, shipper.getLatitude());
            st.setDouble(8,shipper.getLongitude());
            st.setString(9, shipper.getToken());
            st.setTimestamp(10, new Timestamp(shipper.getLastSeen().getTime()));
            st.setInt(11, shipper.getId());

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

    public static List<Shipper> getShippers(){
        List<Shipper> result=new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con=getConnection();

            st = con.prepareStatement("SELECT id,name,active,shipper_type,vehicle,email,reputation,last_latitude,last_longitude,token, last_seen FROM shipper");
            rs = st.executeQuery();

            while (rs.next()) {
                result.add(new Shipper(rs.getInt(1), rs.getString(2),rs.getBoolean(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getDouble(8),rs.getDouble(9),rs.getString(10), (Date) rs.getTimestamp(11)));
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


    public static Shipper getShipper(String email){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con=getConnection();

            st = con.prepareStatement("SELECT id,name,active,shipper_type,vehicle,email,reputation,last_latitude,last_longitude,token, last_seen FROM shipper WHERE email=?");
            st.setString(1,email);
            rs = st.executeQuery();

            if (rs.next()) {
                return new Shipper(rs.getInt(1), rs.getString(2),rs.getBoolean(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getDouble(8),rs.getDouble(9),rs.getString(10), (Date) rs.getTimestamp(11));
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


    public static Shipper getShipper(int shipperId){
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con=getConnection();

            st = con.prepareStatement("SELECT id,name,active,shipper_type,vehicle,email,reputation,last_latitude,last_longitude,token, last_seen FROM shipper WHERE id=?");
            st.setInt(1, shipperId);
            rs = st.executeQuery();

            if (rs.next()) {
                return new Shipper(rs.getInt(1), rs.getString(2), rs.getBoolean(3), rs.getString(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getDouble(8), rs.getDouble(9),rs.getString(10), (Date) rs.getTimestamp(11));
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


}
