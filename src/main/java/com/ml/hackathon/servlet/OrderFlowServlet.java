package com.ml.hackathon.servlet;

import com.google.gson.Gson;
import com.ml.hackathon.ApplicationControllerBean;
import com.ml.hackathon.db.OrderDao;
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
        Long orderId = new Long(0);
        try {
            orderId = Long.valueOf(request.getParameter("order_id"));
        } catch (NumberFormatException nfe) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getOutputStream().print("No puede parsear order_id");
        }

        boolean accept = Boolean.parseBoolean(request.getParameter("accept"));
        if (accept) {


            Order order;
            try {
                order = OrderDao.getOrderById(orderId);

                if (order == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getOutputStream().print("No existe order con order_id " + orderId);
                    return;
                }

                Gson gson = new Gson();
                response.setStatus(HttpServletResponse.SC_OK);
                response.getOutputStream().print(gson.toJson(new ResponseData(order)));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getOutputStream().print(e.getMessage());
            }
        }

        // TODO Marcar order como "en proceso", asociar order con shipper, etc.


    }

    private static class ResponseData
    {
        Order order;

        ResponseData(Order o) { order = o; }
    }


    public void destroy()
    {
    }
}
