package com.ml.hackathon.servlet;

import com.google.gson.Gson;
import com.ml.hackathon.ApplicationControllerBean;
import com.ml.hackathon.db.OrderDao;
import com.ml.hackathon.db.ShippersDao;
import com.ml.hackathon.domain.Order;
import com.ml.hackathon.domain.Shipper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gurodriguez
 */

//curl "localhost:8080/status?order_id={order_id}&user_id={user_id}&status={picked_up|delivered}"
public class StatusChangeServlet extends HttpServlet {

    private ApplicationControllerBean appBean;

    public void init() throws ServletException
    {
        appBean= (ApplicationControllerBean)getServletContext().getAttribute(ApplicationControllerBean.BEAN_NAME);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        Long orderId = null;
        Integer shipperId = null;

        try {
            orderId   = Long.valueOf(request.getParameter("order_id"));
            shipperId = Integer.valueOf(request.getParameter("user_id"));
        } catch (NumberFormatException nfe) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().print("No puede parsear order_id, user_id");
            return;
        }

        String status = request.getParameter("status");
        // TODO validar status

        Order order = null;
        Shipper shipper = null;

        try {
            order = OrderDao.getOrderById(orderId);
            shipper = ShippersDao.getShipper(shipperId);

            if (order == null || shipper == null) throw new Exception("No encuentra Order o Shipper");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getOutputStream().print(e.getMessage());
            return;
        }

        if (order.getShipperId() != shipper.getId()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().print("Order " + orderId + " no pertenece a user " + shipperId);
            return;
        }

        try {
            order.setStatus(status);
            OrderDao.updateOrderStatus(orderId, status);

            Gson gson = new Gson();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().print(gson.toJson(order));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getOutputStream().print(e.getMessage());
        }
    }


    public void destroy()
    {
    }
}
