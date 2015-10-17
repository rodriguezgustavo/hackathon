package com.ml.hackathon.servlet;

import com.google.gson.Gson;
import com.ml.hackathon.ApplicationControllerBean;
import com.ml.hackathon.db.OrderDao;
import com.ml.hackathon.db.ShippersDao;
import com.ml.hackathon.domain.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

                if (order.getShipperId() != null) {
                    responseData = new ResponseData();
                    responseData.status = "rejected";
                } else {
                    responseData = new ResponseData(order, "ok");
                    order.setShipperId(shipperId);
                    OrderDao.updateOrderShipper(orderId, shipperId);
                }

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
        String status;
        ResponseData() {}

        ResponseData(Order o, String st) { order = o; status = st; }
    }


    public void destroy()
    {
    }
}
