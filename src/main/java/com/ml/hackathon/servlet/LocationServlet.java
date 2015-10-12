package com.ml.hackathon.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Created by gurodriguez
 */

//curl "localhost:8080/location?session_id=oeoe&latitude=eee&longitude=1122"
public class LocationServlet extends HttpServlet {

    public void init() throws ServletException
    {
        // Do required initialization
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        String sessionId=request.getParameter("session_id");
        String latitude=request.getParameter("latitude");
        String longitude = request.getParameter("longitude");

        System.out.println("Request from "+sessionId+"; lat:"+latitude+";long:"+longitude);

    }

    public void destroy()
    {
        // do nothing.
    }
}