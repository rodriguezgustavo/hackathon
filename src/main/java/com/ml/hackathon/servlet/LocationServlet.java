package com.ml.hackathon.servlet;

import com.ml.hackathon.ApplicationControllerBean;
import com.ml.hackathon.db.ShippersDao;
import com.ml.hackathon.domain.Session;
import com.ml.hackathon.domain.Shipper;
import com.ml.hackathon.util.SessionUtil;

import java.io.*;
import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Created by gurodriguez
 */

//curl "localhost:8080/location?shipper_id={id}&latitude={lat}&longitude={long}
public class LocationServlet extends HttpServlet {

    private ApplicationControllerBean appBean;

    public void init() throws ServletException
    {
        appBean= (ApplicationControllerBean)getServletContext().getAttribute(ApplicationControllerBean.BEAN_NAME);
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        int shipperId=Integer.parseInt(request.getParameter("shipper_id"));
        double latitude=Double.valueOf(request.getParameter("latitude"));
        double longitude = Double.valueOf(request.getParameter("longitude"));

        System.out.println("Request from "+shipperId+"; lat:"+latitude+";long:"+longitude);

        Shipper shipper= ShippersDao.getShipper(shipperId);
        if(shipper!=null){
            shipper.setLatitude(latitude);
            shipper.setLongitude(longitude);
            for(Shipper s:appBean.getShippers()) {
                if(s.getId()==shipperId){
                    s.setLatitude(latitude);
                    s.setLongitude(longitude);
                    break;
                }
            }

            ShippersDao.updateShipper(shipper);
        }
    }

    public void destroy()
    {
        // do nothing.
    }
}