package com.ml.hackathon.servlet;

import com.ml.hackathon.ApplicationControllerBean;
import com.ml.hackathon.db.ShippersDao;
import com.ml.hackathon.domain.Shipper;

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
        Long orderId=Long.valueOf(request.getParameter("order_id"));
        boolean accept=Boolean.parseBoolean(request.getParameter("accept"));

        System.out.println("Order "+orderId+"; accept:"+accept);


        response.setStatus(HttpServletResponse.SC_OK);
        //TODO
        response.getOutputStream().print("nombre, tel, address, location de seller y buyer");
    }


    public void destroy()
    {
    }
}