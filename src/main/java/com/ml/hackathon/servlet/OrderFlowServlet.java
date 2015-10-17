package com.ml.hackathon.servlet;

import com.google.gson.Gson;
import com.ml.hackathon.ApplicationControllerBean;
import com.ml.hackathon.db.OrderDao;
import com.ml.hackathon.db.ShippersDao;
import com.ml.hackathon.domain.Order;
import com.ml.hackathon.domain.OrderStatus;
import com.ml.hackathon.domain.Shipper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by gurodriguez
 */

//curl "localhost:8080/flow?order_id={order_id}&accept={true|false}&user_id={id}"
public class OrderFlowServlet extends HttpServlet {

    private ApplicationControllerBean appBean;

    public void init() throws ServletException
    {
        appBean= (ApplicationControllerBean)getServletContext().getAttribute(ApplicationControllerBean.BEAN_NAME);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        Integer shipperId = null;
        Long orderId = null;


        try {
            shipperId = Integer.valueOf(request.getParameter("user_id"));
            orderId = Long.valueOf(request.getParameter("order_id"));
        } catch (NumberFormatException nfe1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().print("No puede parsear order_id, user_id");
            return;
        }

        boolean accept = Boolean.parseBoolean(request.getParameter("accept"));
        System.out.println("Flow request:order_id"+orderId+";accept:"+accept+";user_id:"+shipperId);

        if (accept) {

            Order order;
            try {

                // TODO esto debería estar sincronizado

                if (ShippersDao.getShipper(shipperId) == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getOutputStream().print("No existe shipper con id " + shipperId);
                    return;
                }

                order = OrderDao.getOrderById(orderId);

                if (order == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getOutputStream().print("No existe order con order_id " + orderId);
                    return;
                }

                ResponseData responseData;

                //if (order.getShipperId() != null) {
                //    responseData = new ResponseData();
                //    responseData.accepted = "false";
                //} else {
                    responseData = new ResponseData(order, "true");
                    order.setShipperId(shipperId);
                    order.setStatus(OrderStatus.ACCEPTED);
                    OrderDao.updateOrderShipper(orderId, shipperId, OrderStatus.ACCEPTED);

                    for(Order o:appBean.getOrders()) {
                        if(o.getOrderId().equals(orderId)){
                            o.setShipperId(shipperId);
                            o.setStatus(OrderStatus.ACCEPTED);
                            break;
                        }
                    }
                //}

                Gson gson = new Gson();
                response.setStatus(HttpServletResponse.SC_OK);
                response.getOutputStream().print(gson.toJson(responseData));


            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getOutputStream().print(e.getMessage());
            }
        }

    }

    private static class ResponseData
    {
        Order order;
        String accepted;
        ResponseData() {}

        ResponseData(Order o, String st) { order = o; accepted = st; }
    }


    public void destroy()
    {
    }
}
