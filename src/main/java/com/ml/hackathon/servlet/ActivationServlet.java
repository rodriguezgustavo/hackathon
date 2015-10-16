package com.ml.hackathon.servlet;

import com.ml.hackathon.ApplicationControllerBean;
import com.ml.hackathon.db.ShippersDao;
import com.ml.hackathon.db.UserDao;
import com.ml.hackathon.domain.Session;
import com.ml.hackathon.domain.Shipper;
import com.ml.hackathon.util.SessionUtil;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gurodriguez
 */

//curl "localhost:8080/activate?email={mail}&active={true|false}"
public class ActivationServlet extends HttpServlet {

    private ApplicationControllerBean appBean;

    public void init() throws ServletException
    {
        appBean= (ApplicationControllerBean)getServletContext().getAttribute(ApplicationControllerBean.BEAN_NAME);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        String email=request.getParameter("email");
        boolean active=Boolean.parseBoolean(request.getParameter("active"));

        System.out.println("Request from " + email + "; active:" + active);

        Shipper shipper=ShippersDao.getShipper(email);
        if(shipper!=null){
            shipper.setActive(active);
            for(Shipper s:appBean.getShippers()) {
                if (s.getEmail().equals(email)) {
                    s.setActive(active);
                    break;
                }
            }

            ShippersDao.updateShipper(shipper);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().print(shipper.getId());
    }


    public void destroy()
    {
    }
}