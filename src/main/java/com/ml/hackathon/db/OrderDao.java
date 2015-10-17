package com.ml.hackathon.db;

import com.ml.hackathon.domain.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mlabarinas
 */
public class OrderDao extends BaseDao {

    private static Order fillOrder (ResultSet resultSet) throws Exception {
        Order order = new Order();

        order.setId(resultSet.getInt("id"));
        order.setOrderId(resultSet.getLong("order_id"));
        order.setStatus(resultSet.getString("status"));
        order.setSellerId(resultSet.getLong("seller_id"));
        order.setSellerName(resultSet.getString("seller_name"));
        order.setSellerAddress(resultSet.getString("seller_address"));
        order.setSellerNickname(resultSet.getString("seller_nickname"));
        order.setSellerEmail(resultSet.getString("seller_email"));
        order.setSellerPhone(resultSet.getString("seller_phone"));
        order.setReceiverId(resultSet.getLong("receiver_id"));
        order.setReceiverName(resultSet.getString("receiver_name"));
        order.setReceiverAddress(resultSet.getString("receiver_address"));
        order.setReceiverNickname(resultSet.getString("receiver_nickname"));
        order.setReceiverEmail(resultSet.getString("receiver_email"));
        order.setReceiverPhone(resultSet.getString("receiver_phone"));
        order.setItemTitle(resultSet.getString("item_title"));
        order.setItemLatitude(resultSet.getDouble("item_latitude"));
        order.setItemLongitude(resultSet.getDouble("item_longitude"));
        order.setItemQuantity(resultSet.getLong("item_quantity"));
        order.setShipperId(resultSet.getInt("shipper_id"));
        order.setShippingPrice(resultSet.getBigDecimal("shipping_price"));

        return order;
    }

    public static List<Order> getOrders() throws Exception {
        List<Order> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement("select * from shipping_order");

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                result.add(fillOrder(resultSet));
            }

        } catch (SQLException e) {
            throw e;

        } finally {
            try {
                if(resultSet != null) {
                    resultSet.close();
                }

                if(preparedStatement != null) {
                    preparedStatement.close();
                }

                if(connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                throw e;
            }
        }

        return result;
    }



    public static List<Order> getOrdersByStatus(String status) throws Exception {
        List<Order> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement("select * from shipping_order where status = ?");
            preparedStatement.setString(1, status);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                result.add(fillOrder(resultSet));
            }

        } catch (SQLException e) {
            throw e;

        } finally {
            try {
                if(resultSet != null) {
                    resultSet.close();
                }

                if(preparedStatement != null) {
                    preparedStatement.close();
                }

                if(connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                throw e;
            }
        }

        return result;
    }

    public static Order getOrderById(Long id) throws Exception {
        Order result = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement("select * from shipping_order where order_id = ?");
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                result = fillOrder(resultSet);
            }

        } catch (SQLException e) {
            throw e;

        } finally {
            try {
                if(resultSet != null) {
                    resultSet.close();
                }

                if(preparedStatement != null) {
                    preparedStatement.close();
                }

                if(connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                throw e;
            }
        }

        return result;
    }

    public static Integer updateOrderShipper (Long orderId, Integer shipperId) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement("update shipping_order set shipper_id = ? where order_id = ?");
            preparedStatement.setInt(1, shipperId);
            preparedStatement.setLong(2, orderId);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw e;

        } finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }

                if(connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                throw e;
            }
        }
    }

    public static Integer updateOrderStatus(Long orderId, String status) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement("update shipping_order set status = ? where order_id = ?");
            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, orderId);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw e;

        } finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }

                if(connection != null) {
                    connection.close();
                }

            } catch (SQLException e) {
                throw e;
            }
        }
    }

}
